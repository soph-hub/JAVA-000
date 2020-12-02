package org.geekbang.homework.dynamic.datasource.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geekbang.homework.dynamic.datasource.constant.DataSourceConstants.DataSourceGroup;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DataSource(DataSourceGroup.MASTER)
public @interface Master {
}