const mongoose = require('mongoose'),
      Schema = mongoose.Schema;

const CreditSchema = new Schema({
  _id: Number,
  TARGET: {
    type: Number,
    enum: [0, 1]
  },
  ID: {
    type: Number,
    validate: {
      validator: Number.isInteger,
      message: '{VALUE} is not an integer value'
    }
  },
  TLTimeLast: {
    type: Number,
    validate: {
      validator: Number.isInteger,
      message: '{VALUE} is not an integer value'
    }
  },
  TLCnt03: {
    type: Number,
    validate: {
      validator: Number.isInteger,
      message: '{VALUE} is not an integer value'
    }
  },
  TLSatCnt: {
    type: Number,
    validate: {
      validator: Number.isInteger,
      message: '{VALUE} is not an integer value'
    }
  },
  TLDel60Cnt: {
    type: Number,
    validate: {
      validator: Number.isInteger,
      message: '{VALUE} is not an integer value'
    }
  },
  TL75UtilCnt: {
    type: Number,
    validate: {
      validator: Number.isInteger,
      message: '{VALUE} is not an integer value'
    }
  },
  TLBalHCPct: {
    type: Number
  },
  TLSatPct: {
    type: Number
  },
  TLDel3060Cnt24: {
    type: Number,
    validate: {
      validator: Number.isInteger,
      message: '{VALUE} is not an integer value'
    }
  },
  TLOpen24Pct: {
    type: Number
  }
}, { versionKey: false });

module.exports = mongoose.model('Credit', CreditSchema);
