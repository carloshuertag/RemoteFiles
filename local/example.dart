// Importing core libraries
import 'dart:math';

// Importing files
import 'spacecraft.dart';

void main() {
  String name = 'Voyager I';
  int year = 1977;
  double antennaDiameter = 3.7;
  var flybyObjects = ['Jupiter', 'Saturn', 'Uranus', 'Neptune'];
  var image = {
    'tags': ['saturn'],
    'url': '//path/to/saturn.jpg'
  };
  print('Hello, World!');
  if (year >= 2001) {
    print('21st century: $year');
  } else if (year >= 1901) {
    print('20th century: $year');
  }
  for (var object in flybyObjects) {
    print(object);
  }
  for (int month = 1; month <= 12; month++) {
    print(month);
  }
  while (year < 2016) {
    year += 1;
  }
  int fibonacci(int n) {
    if (n == 0 || n == 1) return n;
    return fibonacci(n - 1) + fibonacci(n - 2);
  }
  num result = fibonacci(12);
  print('Result: $result');
  flybyObjects.where((object) => object.contains('r')).forEach(print);
  Spacecraft voyager = Spacecraft(name, DateTime(1977, 9, 5));
  voyager.describe();
  Spacecraft voyager3 = Spacecraft.unlaunched('Voyager III');
  voyager3.describe();
}
