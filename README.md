# Tutorial Pemrograman Lanjut
## Nayla Farah Nida - 2306213426

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

1. I can't tell exactly how many a unit test should be made in a class, but the goal is
to make a unit test of every possible scenarios if necessary, in other words to achieve a high percentage of code coverage. A 100% code coverage doesn't mean that there are no bugs or errors, since it only ensures that most of the code is executed during testing, and doesn't verify if the code behaves correctly or produces the expected results. 

2. If the new functional test suite has the same setup code from ```CreateProductFunctionalTest.java```, it results in unnecessary duplication. Also, the same setup logic appearing in multiple test classes violates the DRY principle and it will certainly reduce the code quality. It would be more efficient to add the additional functional tests within the existing ```CreateProductFunctionalTest.java``` class to reduce duplication and improve maintainability.
