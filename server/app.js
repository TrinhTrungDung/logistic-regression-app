var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var mongoose = require('mongoose');

var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');
var creditRouter = require('./routes/credit');

var database = require('./config/db');
var env = require('./config/env');

var app = express();

env.get();

// Setup database
mongoose.connect(database.mongodb.uri, {
  user: database.mongodb.username,
  password: database.mongodb.password,
  useNewUrlParser: true
});

mongoose.connection.on('error', () => {
  throw new Error(`Unable to connect to database: ${database.mongodb.uri}`);
});

mongoose.connection.on('connected', () => {
  console.log(`Connected to database: ${database.mongodb.uri}`);
});

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', indexRouter);
app.use('/users', usersRouter);
app.use('/credit', creditRouter);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
