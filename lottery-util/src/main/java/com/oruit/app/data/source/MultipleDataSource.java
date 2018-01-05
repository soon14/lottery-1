package com.oruit.app.data.source;

import java.util.HashMap;
import java.util.Map;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultipleDataSource
  extends AbstractRoutingDataSource
{
  private static final ThreadLocal<String> dataSourceKey = new ThreadLocal();
  private static final Map<String, String> packageDataSource = new HashMap();
  
  public static void setDataSourceKey(String dataSource)
  {
    dataSourceKey.set(dataSource);
  }
  
  public static void usePackageDataSource(String pkgName)
  {
    dataSourceKey.set(packageDataSource.get(pkgName));
  }
  
  protected Object determineCurrentLookupKey()
  {
    String dsName = (String)dataSourceKey.get();
    //dataSourceKey.remove();
    return dsName;
  }
  
  public Map<String, String> getPackageDataSource()
  {
    return packageDataSource;
  }
  
  public void setPackageDataSource(Map<String, String> packageDataSource)
  {
    packageDataSource.putAll(packageDataSource);
  }
}