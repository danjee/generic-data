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

The entity class

```java

@Entity
@Table(name = "USERS")
public class User extends DefaultPersistent{
    
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;
    
    @Column(name = "USERNAME")
    private String username;
    
    @Column(name = "PASSWORD")
    private String password;
    
    @Column(name = "ENABLED")
    private Boolean enabled;
    
    @Column(name = "SUPERVISOR_ID")
    private User supervisor;
    
    @Column(name = "BADGE_NUMBER")
    private String badgeNumber;
    
    //getters and setters goes here
}

```

The controller class

```java
import static ro.fortsoft.genericdata.utils.query.QueryParameter.*;

public class SecurityController {
    
    @Autowired
    protected GenericService genericService;
    
    public User getUserById(Integer id){
        return genericService.get(User.class, id);
    }
    
    public User getUserbyUsername(String username){
        User exampleUser = new User();
        exampleUser.setUsername(username);
        return genericService.getUnique(User.class, exampleUser);
    }
    
    public User getEnabledUsersByBadgeNumberOrdered(String badge){
        return genericService.getList(User.class, QueryParameter.build().where("enabled",EQ, Boolean.TRUE).and("badgeNumber",EQ, badge).orderBy("username", true));
    }
    
    public List getUserCreatedBefore(Date before){
        return genericService.getList(User.class, QueryParameter.build().where("creationDate", LE, before));
    }
    
    public List<User> getUsersWithStatus(String status){
    	return genericService.getList(User.class, QueryParameter.build().where("status", EQ, status));
    } 
    
    public List<User> getUsersWithoutSupervisor(){
        return genericService.getList(User.class, QueryParameter.build().where("supervisor", IS_NULL));
    }
    
    public List<User> getUsersWithBadgeContainingTextAndIgnoreCase(String text){
        return genericService.getList(User.class, QueryParameter.build().where("badgeNumber", ILIKE_ANYWHERE, text));
    }
    
    public List<User> getUsersFromSql(String sql){
        return genericService.getList(User.class, QueryParameter.build().where(sql));
    }
    
    public List<User> getUsersBySomeRestrictions(){
        List<ValueRestriction> restrictions = new ArrayList<>();
        restrictions.add(new ValueRestriction("badgeNumber", EQ, "123456"));
        restrictions.add(new ValueRestriction("enabled", EQ, Boolean.FALSE));
        return genericService.getList(User.class, OR_RESTRICTIONS, restrictions);
    }
    
    public List<User> getUsersBySomeRestrictionsNegate(){
        List<ValueRestriction> restrictions = new ArrayList<>();
        ValueRestriction vrBadgeNumber = new ValueRestriction("badgeNumber", EQ, "123456");
        vrBadgeNumber.setIsNotRestriction(true);
        restrictions.add(vrBadgeNumber);
        ValueRestriction vrEnabled = new ValueRestriction("enabled", EQ, Boolean.FALSE);
        vrEnabled.setIsNotRestriction(true);
        restrictions.add(vrEnabled);
        return genericService.getList(User.class, OR_RESTRICTIONS, restrictions);
    }
}
```
