[![Travis CI Build Status](https://travis-ci.org/danjee/generic-data.png)](https://travis-ci.org/danjee/generic-data)
[![Coverage Status](https://coveralls.io/repos/github/danjee/generic-data/badge.svg?branch=master)](https://coveralls.io/github/danjee/generic-data?branch=master)
[![Maven Central](http://img.shields.io/maven-central/v/ro.fortsoft/generic-data.svg)](http://search.maven.org/#search|ga|1|ro.fortsoft.generic-data)

<!--
[![Issue Stats](http://www.issuestats.com/github/danjee/generic-data/badge/issue?style=flat)](http://www.issuestats.com/github/danjee/generic-data)
[![Issue Stats](http://www.issuestats.com/github/danjee/generic-data/badge/pr?style=flat)](http://www.issuestats.com/github/danjee/generic-data)
-->
[![Stories in Ready](https://badge.waffle.io/danjee/generic-data.png?label=ready&title=Ready)](https://waffle.io/danjee/generic-data)


generic-data
===========


Generic Spring service with Hibernate DAO methods


You can use from Maven central

```xml
<dependency>
    <groupId>ro.fortsoft</groupId>
    <artifactId>generic-data-service</artifactId>
    <version>1.9.0</version>
</dependency>
```

Sample code
-----------

```java
@Autowired
protected GenericService genericService;

...

public List<User> getActiveUsers(){
	return genericService.getList(User.class, QueryParameter.build().where("status", QueryParameter.EQ, "active"));
}

```
