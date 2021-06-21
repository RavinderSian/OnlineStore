# OnlineStore
Online store spring project

This project uses spring along with JUnit for testing.
There is no view in this project but a model and controller are present, data is returned in JSON format. CRUD operations are performed for each model class.

## Postman Requests

The requests are present in the postman directory, which contains a json file. This json file can be imported into postman to give the following:

![](https://github.com/RavinderSian/OnlineStore/blob/main/online-store/screenshots/Postman%20Requests.JPG)

All of the model classes have the same general format of requests (CRUD)

The GET Customer request returns a CustomerDTO object as opposed to a Customer object, this hides information such as credit card number. This was done using ModelMapper in the service layer:
![](https://github.com/RavinderSian/OnlineStore/blob/main/online-store/screenshots/Customer%20Mapping.JPG)

GET CustomerById request: 
![](https://github.com/RavinderSian/OnlineStore/blob/main/online-store/screenshots/Get%20Customer.JPG)

The find customer by id request also has error handling if the customer does not exist, this gives a status 404 response: 
![](https://github.com/RavinderSian/OnlineStore/blob/main/online-store/screenshots/Find%20By%20Id%20No%20Customer.JPG)

This response for a non existent id is present in all requests where an id is present in the URL including in DELETE requests:
![](https://github.com/RavinderSian/OnlineStore/blob/main/online-store/screenshots/Delete%20Customer%20No%20Customer%20Found.JPG)

The DELETE requests return a String when a successful deletion has occured:
![](https://github.com/RavinderSian/OnlineStore/blob/main/online-store/screenshots/Delete%20Customer.JPG)

Saving objects is done by using POST requests, when a save is successful the saved object is returned:
![](https://github.com/RavinderSian/OnlineStore/blob/main/online-store/screenshots/Save%20Customer.JPG)

In the example above where Customer is used, the CustomerDTO is used in place of the customer object to conceal sensitive information.

Any field errors are also handled by returning String messages containing the field error, along with a 400 status:
![](https://github.com/RavinderSian/OnlineStore/blob/main/online-store/screenshots/Customer%20Validation.JPG)

Mapped objects within other classes can also be requested, for example a customers orders:
![](https://github.com/RavinderSian/OnlineStore/blob/main/online-store/screenshots/Get%20Orders.JPG)

This also handles a Customer not existing:

![](https://github.com/RavinderSian/OnlineStore/blob/main/online-store/screenshots/Get%20Orders%20Not%20Found.JPG)

Orders can also be added to a Customer using a GET request where the Customer and Order Id are passed in the URL, this returns a String message when successful:

![](https://github.com/RavinderSian/OnlineStore/blob/main/online-store/screenshots/Add%20Order.JPG)

This request also handles an Order or Customer not existing, but if a Customer and Order do not exist the message for a non existent customer will be displayed only as that is the first variable within the URL.

Some classes such as Category have a PATCH request which also displays a String message when successful:
![](https://github.com/RavinderSian/OnlineStore/blob/main/online-store/screenshots/Update%20Category%20Name.JPG)
