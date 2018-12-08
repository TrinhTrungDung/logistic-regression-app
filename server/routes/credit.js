var express = require('express');
var router = express.Router();

var Credit = require('../models/credit');

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.route('/logistic')
    .get((req, res, next) => {
      Credit.find({})
          .then((credits) => {
            res.json({
              data: credits
            });
          });
    })
    .post((req, res, next) => {
      var credits = [];

      for (var i = 0; i < req.body.data.numOfData; i++) {
        try {
          var id = getRandomInteger(1, 10000);
          var credit = generateCredit(id);
          credit.save((err) => {
            if (err) console.log(err);
          });

          credits.push(credit);
        } catch (err) {
          id = getRandomInteger(1, 10000);
        }
      }

      res.json({
        data: credits
      });
    });

function generateCredit(id) {
  var credit = new Credit({
    _id: id,
    ID: id,
    TLTimeLast: getRandomInteger(1, 500),
    TLCnt03: getRandomInteger(0, 4),
    TLSatCnt: getRandomInteger(0, 100),
    TLDel60Cnt: getRandomInteger(0, 20),
    TL75UtilCnt: getRandomInteger(0, 15),
    TLBalHCPct: getRandomPercent(),
    TLSatPct: getRandomPercent(),
    TLDel3060Cnt24: getRandomInteger(0, 5),
    TLOpen24Pct: getRandomPercent()
  });

  return credit;
}

function getRandomPercent() {
  return Math.random();
}

function getRandomInteger(min, max) {
  return Math.floor(Math.random() * (max - min));
}

module.exports = router;
