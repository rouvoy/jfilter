# LDAP Filter Library

This is a simple library to match POJO against LDAP-like filters.

## Maven compilation

LDAP Filter is a [Maven](http://maven.apache.org "Maven") managed project. All you have to do is to invoke the `install` command from the root directory (`LDAP_FILTER_DIR`):

``` bash
cd $LDAP_FILTER_DIR
mvn install
```


## Maven artefact

Once installed, the LDAP Filter artefacts is available as:

``` xml
<dependency>
    <groupId>org.ldap.filter</groupId>
    <artefactId>ldap-filter</artefactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## API usage

``` java
public class Person {
    String firstname = "John", name = "Doe";
	int age = 20;
	boolean male = true;
	double height = 1.8 ; 

    public static void main(String[] args) {
    Person x = new Person();

    // Creates a POJO filter from the LDAP syntax
    Filter filter = FilterParser.instance.parse("(age>18)");
            
    if (filter.match(x))
        System.out.println(x.firstname+" "+x.name+" is more than 18 years old.");
    }
}
```

Currently, the LDAP Filter library supports the following operators:

| Operator | Description  | Supported types        |
|:--------:|:------------:|:-----------------------|
| =        | equals to    | [String](http://docs.oracle.com/javase/6/docs/api/java/lang/String.html), [Number](http://docs.oracle.com/javase/6/docs/api/java/lang/Number.html), [Object](http://docs.oracle.com/javase/6/docs/api/java/lang/Object.html) |
| ~        | differs form | [String](http://docs.oracle.com/javase/6/docs/api/java/lang/String.html), [Number](http://docs.oracle.com/javase/6/docs/api/java/lang/Number.html), [Object](http://docs.oracle.com/javase/6/docs/api/java/lang/Object.html) |
| >        | more than    | [Number](http://docs.oracle.com/javase/6/docs/api/java/lang/Number.html) |
| <        | less than    | [Number](http://docs.oracle.com/javase/6/docs/api/java/lang/Number.html) |

## Licence

    Copyright (C) 2012 Inria, University Lille 1

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Library General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Library General Public License for more details.

    You should have received a copy of the GNU Library General Public
    License along with this library; if not, write to the
    Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
    Boston, MA  02110-1301, USA.