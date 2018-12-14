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
              if (credit != null) {
                if (req.body.TLTimeFirst >= 0) {
                  credit.TLTimeFirst = req.body.TLTimeFirst;
                }
                if (req.body.TLCnt03 >= 0) {
                  credit.TLCnt03 = req.body.TLCnt03;
                }
                if(req.body.TLSatCnt >= 0) {
                  credit.TLSatCnt = req.body.TLSatCnt;
                }
                if(req.body.TLDel60Cnt >= 0) {
                  credit.TLDel60Cnt = req.body.TLDel60Cnt;
                }
                if(req.body.TL75UtilCnt >= 0) {
                  credit.TL75UtilCnt = req.body.TL75UtilCnt;
                }
                if(req.body.TLBalHCPct >= 0 && req.body.TLBalHCPct <= 1) {
                  credit.TLBalHCPct = req.body.TLBalHCPct;
                }
                if(req.body.TLSatPct >= 0 && req.body.TLSatPct <= 1) {
                  credit.TLSatPct = req.body.TLSatPct;
                }
                if(req.body.TLDel3060Cnt24 >= 0) {
                  credit.TLDel3060Cnt24 = req.body.TLDel3060Cnt24;
                }
                if(req.body.TLOpen24Pct >= 0 && req.body.TLOpen24Pct <= 1) {
                  credit.TLOpen24Pct = req.body.TLOpen24Pct;
                }
                if (req.body.TARGET === 0 || req.body.TARGET === 1) {
                  credit.TARGET = req.body.TARGET;
                }
              }
            credit.save()
                .then((credit) => {
                  res.json({
                    data: credit
                  });
                });
        })
        .catch((err) => console.log(err));
    })
    .delete((req, res, next) => {
      Credit.findById(req.params.ID)
          .then((credit) => {
              if (credit != null) {
                credit.remove();
                res.json({
                  success: true,
                  message: `Credit with ID of ${req.params.ID} has been deleted.`
                });
              } else {
                res.json({
                  success: false,
                  message: `Error occurs while deleting credit with ID of ${req.params.ID}`
                });
              }
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
