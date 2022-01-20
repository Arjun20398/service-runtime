# service-runtime
Log runtimes of @service class methods
## Objective
Sometimes we want to know how much time a method is taking. To find that we try to add code with start and end time and then log difference in milliseconds. I have tried making a no-code dependency that will log runtimes of methods in a special JSON format. 

## How to use
1. Add following dependency
```
<dependency>
  <groupId>io.github.arjun20398</groupId>
  <artifactId>service-runtimes</artifactId>
  <version>0.0.4</version>
</dependency>
```
2. add property in properties file
```
service.method.runtimes.log=true

```
3. If above property with true value is not added in property file feature will be disabled
4. Try hitting any API where @Service annotated class is involved. A JSON log will be printed in the console which will be self-explanatory.


## How does it work?
1. @Before advice on @RestController methods which initialize a LOG_EXECUTION_MODEL for that request
2. Populate a list 
   - With className, methodName and startTime
   - With className, methodName and endTime
3. The logic of populating actual JSON objects. This was the most logical part.
4. @After advice on @RestController to print LogModel


## Bonus part!!
/runtimes endpoint will give you 10 most recent LogModels, You don’t need to create this controller this will be automatically created.

## Learnings
1. Joinpoint, Advice, Pointcut, AOP Proxy, Aspect
2. How annotations are created and used
3. Bean Creation Cycle
4. Spring Factories
5. How to create a package that can be used in multiple services
6. How to deploy a package to maven central

## Alternatives
Jprofiler can also be used, It gives detailed information if we want to minimize runtime. It’s paid, the free trial ends in 7 days.
