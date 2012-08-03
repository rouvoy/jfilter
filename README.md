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
    <artifactId>ldap-filter</artifactId>
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
            
        if (filter.match(x)) // Checks if x matches the filter
            System.out.println(x.firstname+" "+x.name+" is more than 18 years old.");


        // Creates a POJO filter from the JSON syntax
        Filter filter = FilterParser.instance.parse("{name : Doe}");
            
        if (filter.match(x)) // Checks if x matches the filter
            System.out.println(x.firstname+"'s name is "+x.name);
    }
}
```

Currently, the library supports the following LDAP filters:

| Operator | Description  | Supported types | Filter example |
|:--------:|:------------:|:----------------| --------------:|
| `=`      | *equals to*  | [String](http://docs.oracle.com/javase/6/docs/api/java/lang/String.html), [Number](http://docs.oracle.com/javase/6/docs/api/java/lang/Number.html), [Object](http://docs.oracle.com/javase/6/docs/api/java/lang/Object.html) | `(firstname=John)` |
| `~`      | *differs from* | [String](http://docs.oracle.com/javase/6/docs/api/java/lang/String.html), [Number](http://docs.oracle.com/javase/6/docs/api/java/lang/Number.html), [Object](http://docs.oracle.com/javase/6/docs/api/java/lang/Object.html) | `(name~Smith)` |
| `>`      | *more than*  | [Number](http://docs.oracle.com/javase/6/docs/api/java/lang/Number.html) | `(heigth>1.6)` |
| `<`      | *less than*  | [Number](http://docs.oracle.com/javase/6/docs/api/java/lang/Number.html) | `(age<20)` |
| `!`      | *not*        | Filter          | `(!(age<10))` |
| `&`      | *and*        | Filters         | `(&(name=Doe)(firstname=John))` |
| pipe     | *or*         | Filters         | `(|(age<10)(male=true))` |

The library also supports the following JSON-like filters:

| Operator | Description  | Supported types | Filter example |
|:--------:|:------------:|:----------------| --------------:|
| `:`      | *equals to*  | [String](http://docs.oracle.com/javase/6/docs/api/java/lang/String.html), [Number](http://docs.oracle.com/javase/6/docs/api/java/lang/Number.html), [Object](http://docs.oracle.com/javase/6/docs/api/java/lang/Object.html) | `{firstname:John}` |
| `,`      | *and*        | Filters         | `{name:Doe,firstname:John}` |



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