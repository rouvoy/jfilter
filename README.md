# Java Filter Library

This is a Java library to match POJO against LDAP-like or JSON-like filters.


## Maven artefact

### Release
The latest released version of the JFilter library artefact is available as:

``` xml
<dependency>
    <groupId>fr.inria.jfilter</groupId>
    <artifactId>jfilter-library</artifactId>
    <version>1.2</version>
</dependency>
```

### Snapshot
The currently developed version of the JFilter library artefact is available as:

``` xml
<dependency>
    <groupId>fr.inria.jfilter</groupId>
    <artifactId>jfilter-library</artifactId>
    <version>1.3-SNAPSHOT</version>
</dependency>
```

## Maven compilation

JFilter is a [Maven](http://maven.apache.org "Maven") managed project. All you have to do is to invoke the `install` command from the root directory (`JFILTER_DIR`):

``` bash
cd $JFILTER_DIR
mvn install
```



## API usage

### LDAP filters

Currently, the library supports the following LDAP-like filters:

| Operator    | Description       | Supported types | Filter example |
|:-----------:|:-----------------:|:----------------| :--------------|
| `=`         | *equals to*       | [String](http://docs.oracle.com/javase/6/docs/api/java/lang/String.html), [Number](http://docs.oracle.com/javase/6/docs/api/java/lang/Number.html), [Object](http://docs.oracle.com/javase/6/docs/api/java/lang/Object.html) | `(firstname = John)` |
| `~`         | *differs from*    | [String](http://docs.oracle.com/javase/6/docs/api/java/lang/String.html), [Number](http://docs.oracle.com/javase/6/docs/api/java/lang/Number.html), [Object](http://docs.oracle.com/javase/6/docs/api/java/lang/Object.html) | `(name ~ Smith)` |
| `>`         | *more than*       | [Number](http://docs.oracle.com/javase/6/docs/api/java/lang/Number.html) | `(height > 1.6)` |
| `>=`        | *more or equals*  | [Number](http://docs.oracle.com/javase/6/docs/api/java/lang/Number.html) | `(height >= 1.6)` |
| `<`         | *less than*       | [Number](http://docs.oracle.com/javase/6/docs/api/java/lang/Number.html) | `(age < 20)` |
| `<=`        | *less or equals*  | [Number](http://docs.oracle.com/javase/6/docs/api/java/lang/Number.html) | `(age <= 20)` |
| `!`         | *not*             | Filter          | `!(age<10)` |
| `&`         | *and*             | Filters         | `&(name=Doe)(firstname=John)` |
| `PIPE`      | *or*              | Filters         | `PIPE(age<10)(male=true)` |
| *wildcards* | *matches all*     | [String](http://docs.oracle.com/javase/6/docs/api/java/lang/String.html) | `&(firstname=J*)(name=Do?)` |
| *types*     | *conforms to*     | [Object](http://docs.oracle.com/javase/6/docs/api/java/lang/Object.html) |  `(objectClass=Person)` |


``` java
public class Person {
    String firstname = "John", name = "Doe";
	int age = 20;
	boolean male = true;
	double height = 1.8 ;
	Address home = new Address(); 
	
	public static class Address {
	    String street = "Main street", city = "New York";
	    int postcode = 10014;
	}

    public static void main(String[] args) {
        Person x = new Person();
        Collection<Person> col = Collections.singleton(x);

        Filter filter1 = FilterParser.instance.parse("age > 18");
        if (filter1.match(x)) // Checks if x is an adult
            System.out.println(x.firstname+" "+x.name+" is more than 18 years old.");

        Filter filter2 = FilterParser.instance.parse("(home.city=New York)");
        if (filter2.match(x)) // Checks if x matches the LDAP filter
            System.out.println(x.firstname+"'s lives in "+x.home.city);

        Filter filter3 = FilterParser.instance.parse("&(firstname=John)(name=D*)");
        if (filter3.match(x)) // Checks if x matches the LDAP filter
            System.out.println(x.firstname+"'s name starts by \"D\"");
            
        if (filter3.match(col)) // Checks if col matches the LDAP filter
            System.out.println(x.firstname+"'s name starts by \"D\"");

        // Filters the content of a collection to keep adults
        Collection<Person> adults = filter1.filter(col);
    }
}
```

### JSON filters
The library also supports the following JSON-like filters:

| Operator | Description  | Supported types | Filter example |
|:--------:|:------------:|:----------------| --------------:|
| `:`      | *equals to*  | [String](http://docs.oracle.com/javase/6/docs/api/java/lang/String.html), [Number](http://docs.oracle.com/javase/6/docs/api/java/lang/Number.html), [Object](http://docs.oracle.com/javase/6/docs/api/java/lang/Object.html) | `{firstname:John}` |
| `,`      | *and*        | Filters         | `{name:Doe,firstname:John}` |
| *wildcards* | *matches*  | [String](http://docs.oracle.com/javase/6/docs/api/java/lang/String.html) | `{firstname:J*,name:Do?}` |
| *types*  | *conforms to* | [Object](http://docs.oracle.com/javase/6/docs/api/java/lang/Object.html) |  `{objectClass:Person}` |


``` java
public class Person {
    String firstname = "John", name = "Doe";
	int age = 20;
	boolean male = true;
	double height = 1.8 ;
	Address home = new Address(); 
	
	public static class Address {
	    String street = "Main street", city = "New York";
	    int postcode = 10014;
	}

    public static void main(String[] args) {
        Person x = new Person();
        Collection<Person> col = Collections.singleton(x);

        // Creates a POJO filter from the JSON syntax
        Filter filter1 = FilterParser.instance.parse("{name:Doe}");
            
        if (filter1.match(x)) // Checks if x matches the JSON filter
            System.out.println(x.firstname+"'s name is "+x.name);

        if (filter1.match(col)) // Checks if col matches the JSON filter
            System.out.println(x.firstname+"'s name is "+x.name);
            
        // Filters the content of a collection to keep names starting by D
        Filter filter2 = FilterParser.instance.parse("{name:D*}");            
        Collection<Person> named = filter2.filter(col);
    }
}
```

## Licence

    Copyright (C) 2012 University Lille 1, Inria

    This library is free software; you can redistribute it and/or modify
    it under the terms of the GNU Library General Public License as published
    by the Free Software Foundation; either version 2 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Library General Public
    License for more details.

    You should have received a copy of the GNU Library General Public License
    along with this library; if not, write to the Free Software Foundation,
    Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301, USA.