# Dining-Philosopher-Problem
This repository contains files for the Dining-Philosopher-Problem for Operating Systems course Comp-346

## Description of the problem

This project is a slightly extended version of the classical synchronization problem : [**Dining Philosopher Problem**](https://en.wikipedia.org/wiki/Dining_philosophers_problem). The slight extension refers to the fact that sometimes philosophers would like to talk, but only one (any) philosopher can be talking at a time while they are not eating. A philosopher can only be talking for a limited amount of time, and while one philosopher is talking, none of the other philosophers can sleep; however they can be eating or thinking. The problem is solved using Monitor Synchronization Construct built on top of Java's synchronization primitives. 

## File List

##### Common file
- BaseThread.java
##### Task-1 to Task-6
- Monitor.Java
- Philosopher.java
- DiningPhilosophers.java


## Built with

* [**Java**](https://en.wikipedia.org/wiki/Java_(programming_language)) - The programming language used
* [**Eclipse**](https://en.wikipedia.org/wiki/Eclipse_(software)) - The IDE used 


## Author(s)

* [**Mushfiqur Anik**](https://github.com/mushfiqur-anik)

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
