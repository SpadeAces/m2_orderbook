# m2_orderbook 

A Simple orderboot with market and limit order types, supporting icebergs and bracketed orders using mongodb, 

The project includes a data seeder to feed some orders to the db at run time. 

include endpoints 

http://localhost:8080/order/get_orderbook?symbol=SOLUSDT  -> GET -> for getting order book

http://localhost:8080/order -> POST -> for posting order book

http://localhost:8080/order?id=1 -> GET -> for getting details of specific order based on id

http://localhost:8080/order/update_order -> POST for updating already exisiting order. 
