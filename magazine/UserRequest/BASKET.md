
request2: addProduct: <200 OK OK,Product(ID=1, name=chocalate, price=700.0)added in basket,[]>

request3: addProduct: <200 OK OK,Product(ID=1, name=chocolate, price=200.0)added in basket,[]>

request4: addProduct: <200 OK OK,Product(ID=2, name=orange, price=500.0)added in basket,[]>

request5: basketList: <200 OK OK,[Product(ID=1, name=chocolate, price=200.0), Product(ID=2, name=orange, price=500.0)],[]>

request6: payBasket: <409 CONFLICT Conflict,Basket NOT payed!,[]>

request7: payBasket: <200 OK OK,Basket payed! change=100.0,[]>
