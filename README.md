# OnlineStore
Online store spring project

This project uses spring along with JUnit for testing.
There is no view in this project but a model and controller are present, data is returned in JSON format. CRUD operations are performed for each model class.

## Postman Requests

The requests are present in the postman directory, which contains a json file. This json file can be imported into postman to give the following:

![](https://github.com/RavinderSian/OnlineStore/blob/main/online-store/screenshots/Postman%20Requests.JPG)

All of the model classes have the same general format of requests (CRUD)

The GET Customer request returns a CustomerDTO object as opposed to a Customer object, this hides information such as credit card number. This was done using ModelMapper in the service layer:



![](https://github.com/RavinderSian/OnlineStore/blob/main/online-store/screenshots/Get%20Customer.JPG)

