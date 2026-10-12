# TCSS 305: Object Equality

Lecture project for TCSS 305 Programming Practicum, Week 2 (Autumn 2026), UW Tacoma.

- `src/edu/uw/tcss/equality/Point.java`: a mutable point. Both versions of `equals`,
  one using `getClass()` and one using `instanceof`, are commented out with notes
  comparing them. Until you uncomment one, `Point` uses `Object`'s `equals`, which is `==`.
- `test/edu/uw/tcss/equality/PointTest.java`: the starter for the tests written live in lecture.
- `reference/PointTestFinished.java`: where the tests end up, plus two demos that fail on
  purpose. The folder is excluded from the build; copy the file into `test/` to run it.

Open the folder in IntelliJ IDEA with JDK 25. JUnit 6 is resolved from Maven by the module file.
