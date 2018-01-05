/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.data.source;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;

public class MultipleDataSourceAspect implements MethodBeforeAdvice {
  private static final Logger log = LoggerFactory.getLogger(MultipleDataSourceAspect.class);

  @Override
  public void before(Method method, Object[] args, Object target) throws Throwable {
    if (method.isAnnotationPresent(DataSource.class)) {
      dsSettingInMethod(method);
      return;
    }
    Class declaringClazz = method.getDeclaringClass();
    if (declaringClazz.isAnnotationPresent(DataSource.class)) {
      try
      {
        dsSettingInConstructor(declaringClazz);
        return;
      }
      catch (Exception e)
      {
        log.debug("get DataSource error ", e);
      }
    }
    Package pkg = declaringClazz.getPackage();
    dsSettingInPackage(pkg);
  }

  private String dsSettingInMethod(Method method)
  {
    DataSource dataSource = (DataSource)method.getAnnotation(DataSource.class);
    // 给指定方法设置数据源
    MultipleDataSource.setDataSourceKey(dataSource.value());
    return dataSource.value();
  }
  
  private String dsSettingInConstructor(Class clazz)
  {
    DataSource dataSource = (DataSource)clazz.getAnnotation(DataSource.class);
    return dataSource.value();
  }
  
  private void dsSettingInPackage(Package pkg)
  {
    if (log.isDebugEnabled()) {
      log.debug(pkg.getName());
    }
    MultipleDataSource.usePackageDataSource(pkg.getName());
  }


}
