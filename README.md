# Tutorial Pemrograman Lanjut
## Nayla Farah Nida - 2306213426

🔗[Eshop Deployment](adpro-naylafarah.koyeb.app/)

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

2. Yes, I think the current implementation aligns with the concept of CI and CD. Continuous Integration (CI) involves implementation and testing, and I’m using ```ci.yml```, ```scorecard.yml```, and ```sonarcloud.yml``` to automatically test my code on push and pull requests. Continuous deployment (CD) involves deployment and maintenance, I use Koyeb to automatically deploy my app on push and pull request. Overall, my setup that automates testing and deployment is working well, so I'd say that this implementation is aligning with CI/CD principles :D
   
</details>
