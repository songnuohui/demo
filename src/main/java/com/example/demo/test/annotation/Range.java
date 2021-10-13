package com.example.demo.test.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 一个名叫Range的注解
 *
 * @author SongNuoHui
 * @date 2021/10/13 15:48
 */

/**
 * 仅编译期：RetentionPolicy.SOURCE；
 * 仅class文件：RetentionPolicy.CLASS；
 * 运行期：RetentionPolicy.RUNTIME。
 */

/**
 * TYPE:作用在类，接口，或者枚举类上的
 * FIELD:在字段上的
 * METHOD:方法
 * PARAMETER:参数
 * CONSTRUCTOR:构造方法
 * LOCAL_VARIABLE:用于描述局部变量
 * PACKAGE:包
 * TYPE_USE:使用类型，1.8开始
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Range {

	int min() default 0;

	int max() default 255;

}
