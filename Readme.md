# Composite Conditional Pattern
This repos implements the Composite Conditional pattern, and provides an example of it's usage using test cases.

## Table of Contents
- [Introduction](#introduction)
- [Installation](#installation)
- [Example usage](#Usage)
- [License](#license)

## Introduction
This pattern is an offshoot of the composite pattern.
The composite conditional pattern uses the composite pattern to separate a complex conditional logic, from the actual comparison logic.
Since the conditions are separated from the actual comparison logic, the conditions can be injected into the code as configs.

The pattern is useful, if your code has to select one or more values from a list of options, where each option has a list of conditions that have to be satisfied for selection. The pattern becomes even more useful, if you want the user of the code to be able to change the options list and their conditions after the code is delivered.

## Usage
Imagine you had to implement a function that would take a list of student names and their scores in Maths and Science and output a grade to be
assigned to the student.
Award 
1. ```Topper``` grade if the student gets 100 in both Maths and Science.
2. ```A++``` grade if the student gets 100 in atleast one subjects, and more than 95 in the other.
3. ```A+``` grade if the student gets 
    1. 100 in one subject and more than 90 in the other.
    2. more than 95 in both subjects.

At first you would write a code as shown below.

```java
public class StudentGrader {
    public static String assignGrade(int mathScore, int scienceScore) {
        if (mathScore == 100 && scienceScore == 100) {
            return "Topper";
        } else if ((mathScore == 100 && scienceScore > 95) || (scienceScore == 100 && mathScore > 95)) {
            return "A++";
        } else if ((mathScore == 100 && scienceScore > 90) || (scienceScore == 100 && mathScore > 90) || (mathScore > 95 && scienceScore > 95)) {
            return "A+";
        } else {
            return "No grade";
        }
    }
}
```

But this kind of code creates a hard codes the conditions for assigning a topic and the topics which can be assigned into the code, limiting the configurability of the code. Also, the example code looks as simple as it does, becasue we have only considered only 2 subjects. The code complexity will increase exponentially if the number of subjects to be evaluated would increase to 6 or 10.

The composite conditional solves this problem by separating the options/conditions list from the code which actually does the comparision.

The above function has been implemented using the composite conditional pattern in ```StudentSegregatorTest.java``` test case.

## License
This project is licensed under the MIT License.
