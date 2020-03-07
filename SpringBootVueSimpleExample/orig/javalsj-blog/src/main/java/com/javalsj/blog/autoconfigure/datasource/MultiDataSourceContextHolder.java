package com.javalsj.blog.autoconfigure.datasource;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.google.common.base.Objects;
import com.javalsj.blog.autoconfigure.datasource.MultiDataSourceService;

/**
 * @description 支持多数据源场景下动态切换数据源名称的上下文
 * @author WANGJIHONG
 * @date 2018年2月28日 下午7:34:27
 * @Copyright 版权所有 (c) www.javalsj.comx
 * @memo 无备注信息
 */
public final class MultiDataSourceContextHolder {

	private final static Logger logger = LoggerFactory.getLogger(MultiDataSourceContextHolder.class);

	@Autowired
	private static MultiDataSourceService multiDataSourceService;

	/**
	 * 使用ThreadLocal维护变量，ThreadLocal为每个使用该变量的线程提供独立的变量副本。
	 * 存储当前线程的数据源切换信息，如运行方法1切换到first数据源，方法1调用方法2，方法2又切换到second数据源，则该变量会记录。
	 * 数据结构格式如下：(null, firstEntry, encondEntry)->(firstEntry, encondEntry,
	 * thirdEntry)->(encondEntry, thirdEntry, null)->......
	 */
	private static final ThreadLocal<MultiDataSourceContextEntry> MULTIDATASOURCE_CONTEXT_ENTRY = new ThreadLocal<MultiDataSourceContextEntry>();

	/**
	 * 默认数据源name标识
	 */
	private static volatile String defaultDataSourceName;

	/**
	 * 获取线程上下文的当前数据源
	 */
	public static String getDataSourceName() {
		MultiDataSourceContextEntry currentDataSourceContextEntry = MULTIDATASOURCE_CONTEXT_ENTRY.get();
		String dataSourceName = null;
		if (currentDataSourceContextEntry == null
				|| StringUtils.isEmpty(currentDataSourceContextEntry.getDataSourceName())) {
			// 若当前现程上下文无数据源，则取默认数据源，若默认数据源是空，则默认取多数据源的第一个作为默认数据源
			if (StringUtils.isEmpty(defaultDataSourceName) && multiDataSourceService != null) {
				Map<Object, Object> customDataSources = multiDataSourceService.getDataSources();
				if (customDataSources != null && customDataSources.size() > 0) {
					dataSourceName = customDataSources.entrySet().iterator().next().getKey().toString();
					defaultDataSourceName = dataSourceName;
				}
			} else {
				dataSourceName = defaultDataSourceName;
			}
		} else {
			dataSourceName = currentDataSourceContextEntry.getDataSourceName();
		}
		return dataSourceName;
	}

	/**
	 * 设置线程上下文当前的数据源
	 * 
	 * @param dataSourceName
	 *            数据源name标识
	 */
	public static void setDataSourceName(String dataSourceName) {
		if (StringUtils.isEmpty(dataSourceName) || !MultiDataSourceContextHolder.existDataSourceName(dataSourceName)) {
			logger.info("系统上下文中当前切换的数据源[{}]不存在！！！系统已自动切换至默认数据源。 ", dataSourceName);
		}

		// 1.获取当前线程上下文正在执行的数据源实体
		MultiDataSourceContextEntry currentDataSourceEntry = MULTIDATASOURCE_CONTEXT_ENTRY.get();
		Object currentDataSourceName = currentDataSourceEntry == null ? null
				: currentDataSourceEntry.getDataSourceName();
		if (Objects.equal(currentDataSourceName, dataSourceName)) {
			// 控制在设置数据源会忽略连续相同数据源重复设置
			return;
		}
		// 2.构建本次设置的数据源链路实体
		MultiDataSourceContextEntry nextDataSourceContextEntry = new MultiDataSourceContextEntry(dataSourceName);
		if (currentDataSourceEntry != null) {
			// 3.设置上下文当前的链路实体的下一个数据源实体为当前设置的实体
			currentDataSourceEntry.setNext(nextDataSourceContextEntry);
		}
		// 4.设置当前构建的数据源链路实体的
		nextDataSourceContextEntry.setBefore(currentDataSourceEntry);
		// 5.设置当前线程上下文正在执行的数据源实体
		MULTIDATASOURCE_CONTEXT_ENTRY.set(nextDataSourceContextEntry);
		logger.info("系统当前上下文中切换了数据源 : [{}] -> [{}]。", currentDataSourceName, dataSourceName);
	}

	/**
	 * 清除线程上下文的当前数据源
	 */
	public static void destroyDataSource(String dataSourceName) {
		// 1.获取当前线程上下文正在执行的数据源实体
		MultiDataSourceContextEntry currentDataSourceEntry = MULTIDATASOURCE_CONTEXT_ENTRY.get();
		// 2.若当前数据源不为空，则设置当前线程上下文正在执行为上一个数据源
		if (currentDataSourceEntry != null) {
			MultiDataSourceContextEntry beforeDataSourceEntry = currentDataSourceEntry.getBefore();
			if (beforeDataSourceEntry == null) {
				// 前一个为空则说明当前销毁的为线程第一个数据源，则清空该线程数据源整个调用链路上下文数据
				MULTIDATASOURCE_CONTEXT_ENTRY.remove();
			} else {
				// 每删除最后一次的数据源时，即把倒数第二个上下问的after实体置空,回收垃圾
				currentDataSourceEntry = null;
				beforeDataSourceEntry.setNext(null);
				// 设置当前线程上下文正在执行为上一个数据源
				MULTIDATASOURCE_CONTEXT_ENTRY.set(beforeDataSourceEntry);
				if (Objects.equal(beforeDataSourceEntry.getDataSourceName(), dataSourceName)) {
					// 若上一个和本次销毁的数据源相同，则继续销毁，直到不同为止。（此处理建立在设置数据源会忽略连续相同数据源重复设置的控制下）
					destroyDataSource(dataSourceName);
					return;
				}
				MultiDataSourceContextHolder.setDataSourceName(beforeDataSourceEntry.getDataSourceName());
				// Object currentDataSourceName = currentDataSourceEntry == null ? null :
				// currentDataSourceEntry.getDataSourceName();
				// logger.info("系统当前上下文中切换的数据源[{}]执行完毕 ，系统当前上下文中切回至数据源 : [{}]。",
				// currentDataSourceName, beforeDataSourceEntry.getDataSourceName());
			}
		}
	}

	/**
	 * 判断是否存在指定标识的数据源
	 * 
	 * @param dataSourceName
	 *            数据源name标识
	 */
	public static boolean existDataSourceName(String dataSourceName) {
		if (multiDataSourceService != null) {
			Object object = multiDataSourceService.getDataSources().get(dataSourceName);
			return object != null;
		} else {
			return false;
		}
	}

}

/**
 * @description 线程数据源上下文中所有切换数据源的调用链路实体 （该实体一般放在ThreadLocal中使用，保证线程隔离安全性）
 * @author WANGJIHONG
 * @date 2018年3月8日 下午2:21:59
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 如运行方法1切换到first数据源，方法1内调用方法2，方法2又切换到second数据源，那么方法2执行完毕应该将线程上下文的数据源切回方法1对于的first数据源，然后继续执行后续逻辑。
 *       该类就是记录这种数据源链路信息的数据结构。(beforeDataSourceEntry(上下文前一个数据源),
 *       dataSourceName(上下文当前数据源), afterDataSourceEntry(上下文后一个数据源))。
 *       数据结构格式如下：(null,null,null)->(null, firstEntry,
 *       encondEntry)->(firstEntry, encondEntry, thirdEntry)->(encondEntry,
 *       thirdEntry, null)->......
 */
class MultiDataSourceContextEntry {
	/**
	 * 线程上下文当前数据源数据项对应的数据源name标识
	 */
	private String dataSourceName;
	/**
	 * 线程上下文前一个数据源标识数据项
	 */
	private MultiDataSourceContextEntry before;
	/**
	 * 线程上下文下一个数据源标识数据项
	 */
	private MultiDataSourceContextEntry next;

	public MultiDataSourceContextEntry(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public MultiDataSourceContextEntry getBefore() {
		return before;
	}

	public void setBefore(MultiDataSourceContextEntry before) {
		this.before = before;
	}

	public MultiDataSourceContextEntry getNext() {
		return next;
	}

	public void setNext(MultiDataSourceContextEntry next) {
		this.next = next;
	}

}