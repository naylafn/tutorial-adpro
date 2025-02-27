# Tutorial Pemrograman Lanjut
## Nayla Farah Nida - 2306213426

🔗[Eshop Deployment](https://adpro-naylafarah.koyeb.app/)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=naylafn_tutorial-adpro&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=naylafn_tutorial-adpro)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=naylafn_tutorial-adpro&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=naylafn_tutorial-adpro)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=naylafn_tutorial-adpro&metric=coverage)](https://sonarcloud.io/summary/new_code?id=naylafn_tutorial-adpro)

<details>
   <summary>Module 1</summary>

### Reflection 1

Here are some clean code principles and secure coding practices that I have applied to my code.

**1. Meaningful Names**
   
Clear and descriptive method names: ```create```, ```delete```, ```findAll```, ```findById```.

Self explanatory parameter names: ```productId```, ```newProduct```.

**2. One Function One Task**

Each method has a single, clear task.

**3. DRY (Don't Repeat Yourself)**

Reuse of ```findById``` and ```findByAll``` methods.

**4. Error Handling**

Error handling for illegal argument in product quantity.

```Java
if (product.getProductQuantity() < 0) {
  throw new IllegalArgumentException("Product quantity cannot be negative");
}  
```

**5. Using UUID for Product IDs**

To prevent sequential guessing of IDs.

```Java
if (product.getProductId() == null){
  product.setProductId(String.valueOf(UUID.randomUUID()));
}
```

**6. Input Validation**

Stripping user's input to prevent injections.

```Java
if (product.getProductName() != null) {
  String sanitizedName = product.getProductName().replaceAll("[<>%$]", "");
  product.setProductName(sanitizedName);
}
```

**Mistakes I find:**
- The product ID is not being correctly generated. So I handle it with setting a UUID for every product.

### Reflection 2

1. I can't tell exactly how many unit test should be made in a class, but the goal is
to make a unit test for every possible scenarios if necessary. In other words, we need to achieve a high percentage of code coverage. A 100% code coverage doesn't mean that there are no bugs or errors, since it only ensures that most of the code is executed during testing, and doesn't verify if the code behaves correctly or produces the expected results. 

2. If the new functional test suite has the same setup code from ```CreateProductFunctionalTest.java```, it results in unnecessary duplication. Also, the same setup logic appearing in multiple test classes violates the DRY principle and it will certainly reduce the code quality. It would be more efficient to add the additional functional tests within the existing ```CreateProductFunctionalTest.java``` class to reduce duplication and improve maintainability.

</details>

<details>
   <summary>Module 2</summary>

### Reflection

1. While improving my project's code coverage, I found a few issues since I was only writing tests for uncovered code based on my JaCoCo report. However, I did notice some minor code quality issues, such as unused imports and unnecessary comments. To address these, I removed any redundant imports to keep the codebase clean and deleted unnecessary comments to improve readability. Additionally, I ensured that the new test cases followed best practices, such as meaningful names and proper test coverage for edge cases. There might still be some inconsistencies or smelly code that got overlooked, but overall I believe the code quality is pretty good. 

**Update,** I have fixed all the code smells :D

Here are some issues I encountered:

**Remove some field injection and use constructor injection instead**

before:
``` java
@Autowired
private ProductService service;
```

after:
``` java
private final ProductService service;
    
    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }
```
         
**Refactor the code of the lambda to have only one invocation possibly throwing a runtime exception**

before:
```java
Exception exception = assertThrows(IllegalArgumentException.class, () -> {
   productRepository.edit(product.getProductId(), updatedProduct);
});
```

after:
```java
Exception exception = assertThrows(IllegalArgumentException.class,
   () -> productRepository.edit(product.getProductId(), updatedProduct));
```

**Add a nested comment explaining why the setup() method is empty**
```java
@BeforeEach
void setUp() {
   // This method is intentionally left empty.
}
```

2. Yes, I think the current implementation aligns with the concept of CI and CD. Continuous Integration (CI) involves implementation and testing, and I’m using ```ci.yml```, ```scorecard.yml```, and ```sonarcloud.yml``` to automatically test my code on push and pull requests. Continuous deployment (CD) involves deployment and maintenance, I use Koyeb to automatically deploy my app on push and pull request. Overall, my setup that automates testing and deployment is working well, so I'd say that this implementation is aligning with CI/CD principles :D
   
</details>

<details>
   <summary>Module 3</summary>

   ### Reflection

   1. SOLID principles I applied to my project:


   **Single Responsibility Principle (SRP)**: Separate the ```ProductController``` and ```CarController``` classes. Each controller should handle different concerns.

   ```ProductController```: Products in general

   ```CarController```: Products specifically for cars

   **Open Closed Principle (OCP)**: OCP states that class should be open for extension but closed for modification. The use of ```CarService``` as an interface for ```CarServiceImpl``` ensure that new behaviors can be added by creating new implementations without modifying the existing code.

   **Liskov Substitution Principle (LSP)**: LSP states that subtypes must be substitutable for their base types without altering the correctness of the program. I remove the inheritance between ```CarController``` and ```ProductController```, because the have different behavior thus forcing ```CarController``` to inherit unnecessary behaviors

   **Interface Segregation Principle (ISP)**: ISP states that clients should not be forced to depend on interfaces they do not use. ```CarService``` interface is reasonably focused on car-specific operations and doesn't contain any methods that wouldn't be needed by classes implementing it.

   **Dependency Inversion Principle (DIP)**: DIP states that high-level modules should not depend on low-level modules, both should depend on abstractions (interfaces). Before solid, ```CarServiceImpl``` directly depends on ```CarRepository``` class rather than an interface. If ```CarRepository changes```, ```CarServiceImpl``` also needs modification. I change it so that ```CarServiceImpl``` depends on ```CarService``` interface.

   2. Advantages of applying SOLID principles:

   SOLID principles improve code quality, maintainability, and scalability. It ensures that modifying or extending my project doesn’t require changing the existing codebase. For example, with DIP ```CarServiceImpl``` depends on abstraction rather than concrete implementations, which makes it easier to mock dependencies for testing without needing a real database connection.
   
   3. Disadvantages of not applying SOLID principles:

   Not applying SOLID principles can lead to fragile and hard-to-maintain code, making future modifications risky and time-consuming. For example, without SRP classes become too dependent on multiple responsibilities, making them harder to debug or modify. Without OCP, changes to one part of the code require modifications across multiple files. Without DIP, classes are tightly coupled to implementations rather than abstractions, making future modifications difficult.
   
</details>
