db = db.getSiblingDB('paymentapi_db')

db.createUser({
    user: 'payment',
    pwd: 'payment',
    roles: [
        {
            role: "readWrite",
            db: 'paymentapi_db',
        },
    ],
});
