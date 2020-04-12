var express = require('express');
var fs = require('fs');
var bodyParser = require('body-parser')
var app = new express();
app.use(express.static('web'));
app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(bodyParser.json());
var db = __dirname + '/db';

var port = 3600;

app.get('/', (req, res) => {
    res.send("I am listening");
})

app.post('/api/cspots', (req, res) => {
    console.log(req);
    return res.sendFile(db + '/cspots.json');
})

app.listen(port, '0.0.0.0', () => {
    console.log('server is listening on port: ' + port);
})

