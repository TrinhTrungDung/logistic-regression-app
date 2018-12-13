var express = require('express');
var router = express.Router();

var Credit = require('../models/credit');

router.route('/')
    .get((req, res, next) => {
      Credit.find({})
          .then((credits) => {
            res.json({
              data: credits
            });
          });
    })
    .post((req, res, next) => {
      try {
        var id = getRandomInteger(1, 10000);
        var credit = new Credit({
          _id: id,
          ID: id,
          TLTimeFirst: req.body.TLTimeFirst,
          TLCnt03: req.body.TLCnt03,
          TLSatCnt: req.body.TLSatCnt,
          TLDel60Cnt: req.body.TLDel60Cnt,
          TL75UtilCnt: req.body.TL75UtilCnt,
          TLBalHCPct: req.body.TLBalHCPct,
          TLSatPct: req.body.TLSatPct,
          TLDel3060Cnt24: req.body.TLDel3060Cnt24,
          TLOpen24Pct: req.body.TLOpen24Pct,
          TARGET: req.body.TARGET
        });
        credit.save((err) => {
          if (err) console.log(err);
        });
      } catch (err) {
        id = getRandomInteger(1, 1000000);
      }

      res.json({
        data: credit
      });
    });

router.route('/:ID')
    .put((req, res, next) => {
      Credit.findById(req.params.ID)
        .then((credit) => {
          console.log(credit);
          if (credit != null) {
            if (req.body.TLTimeFirst) {
              console.log(req.body.TLTimeFirst);
            }
            if(req.body.TLCnt03) {
              console.log(req.body.TLCnt03);
            }
            if(req.body.TLSatCnt) {
              console.log(req.body.TLSatCnt);
            }
            if(req.body.TLDel60Cnt) {
              console.log(req.body.TLDel60Cnt);
            }
            if(req.body.TL75UtilCnt) {
              console.log(req.body.TL75UtilCnt);
            }
            if(req.body.TLBalHCPct) {
              console.log(req.body.TLBalHCPct);
            }
            if(req.body.TLSatPct) {
              console.log(req.body.TLSatPct);
            }
            if(req.body.TLDel3060Cnt24) {
              console.log(req.body.TLDel3060Cnt24);
            }
            if(req.body.TLOpen24Pct) {
              console.log(req.body.TLOpen24Pct);
            }
          }
          // credit.save()
          //     .then((credit) => {
          //
          //     });
          res.json({
            test: "test"
          });
        });
    });

router.route('/random')
    .post((req, res, next) => {
      var credits = [];

      for (var i = 0; i < req.body.data.numOfData; i++) {
        try {
          var id = getRandomInteger(1, 1000000);
          var credit = generateCredit(id);
          credit.save((err) => {
            if (err) console.log(err);
          });

          credits.push(credit);
        } catch (err) {
          id = getRandomInteger(1, 1000000);
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
    TLTimeFirst: getRandomInteger(1, 500),
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
