# TCSS 305: Object Equality

Lecture project for TCSS 305 Programming Practicum, Week 2 (Autumn 2026), UW Tacoma.

- `src/edu/uw/tcss/equality/Point.java`: a mutable point with two versions of `equals`.
  The `getClass()` version is live and the `instanceof` version is commented out, with notes
  comparing them. Swap them by selecting a method and pressing Cmd+/ (Ctrl+/).
- `Point` also validates: a negative coordinate throws `IllegalArgumentException`, and the copy
  constructor throws `NullPointerException` for null, so the lecture can demo testing for exceptions.
- `test/edu/uw/tcss/equality/PointTest.java`: the starter for the tests written live in lecture.
  The null test is provided; the negative-coordinate test is written live.
- `reference/PointTestFinished.java`: where the tests end up, plus two demos that fail on
  purpose. The folder is excluded from the build; copy the file into `test/` to run it.

Open the folder in IntelliJ IDEA with JDK 25. JUnit 6 is resolved from Maven by the module file.
