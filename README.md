### MOP (**M**apping **O**ptimizer **P**lugin)

***
## Table of contents

* [1. License](#1-license)
* [2. Overview](#2-overview)
* [3. Usage](#3-usage)
* [4. Example](#4-example)
## 1. License

See the [LICENSE](../blob/master/LICENSE) file for license rights and limitations (AGPL-3.0).

## 2. Overview

This is a javac plugin which can easily be plugged-in with Java compiler by adding vm argument `-Xplugin:MOP` so that we can write mapping code without any more `null` checks and instance creations and this plugin will take care adding null checks, instance creation statements during bytecode generation.

## 3. Usage
In java source code, the mapping methods can be annotated as below so that this plugin will modify byte code of those methods during gradle task `:compileJavaClasses`.
```java
@MethodInstrument(type=MethodInstrumentType.MAPPING)
```
or if want to add only null check without instance creation statements , then we can annotate methods as below.
```java
@MethodInstrument(type=MethodInstrumentType.NULL_FREE)
```
Your *Gradle* project can be configured to use this plugin as below:
##### _build.gradle_ 
```groovy
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies{
    classpath('com.github.navisarv:mop-gradle:1.0.1')
    }
}
... ... ...
... ... ...
apply plugin: 'mop-gradle'
```
##### dependencies.gradle
```groovy
compile('com.github.navisarv:mop-appliance:1.0.0')
```

## 4. Example

Consider below mapping code:
```java
@MethodInstrument(type=MethodInstrumentType.MAPPING)
public Employee mapEmployee(Employee emp) {
	Employee rEmp = null;
	rEmp.getAddress().getZip().setZip5(emp.getAddress().getZip().getZip5());
	return rEmp;
}
```
This plugin modifies resulting bytecode as the source code looks like below:
```java
@MethodInstrument(type = MethodInstrumentType.MAPPING)
public Employee mapEmployee(Employee emp) {
	Employee rEmp = null;
	if (emp != null && (emp.getAddress() != null
		&& (emp.getAddress().getZip() != null && emp.getAddress().getZip().getZip5() != null))) {
	boolean initFieldSts315607881988100 = true;
	if (rEmp == null) {
	    rEmp = this.new Employee();
	 }
	 if (rEmp.getAddress() == null) {
	    com.github.navisarv.mop.appliance.util.ReflUtil.initField(this, rEmp, "getAddress");
	 }
	 if (rEmp.getAddress().getZip() == null) {
	     com.github.navisarv.mop.appliance.util.ReflUtil.initField(this, rEmp.getAddress(), "getZip");
	 }
	 if (initFieldSts315607881988100 == true) {
	     rEmp.getAddress().getZip().setZip5(emp.getAddress().getZip().getZip5());
	 }
	}
	return rEmp;
}
```
