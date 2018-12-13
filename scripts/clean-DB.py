#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Mon Dec 10 11:43:46 2018

@author: Hoang
Scheme:
    - As no information on data collection was available,
    complex imputation methods are not utilized
    - All entries with missing InqTimeLast (Duration since financial
    statement was last requested) all has a inqury count of 0, all missing
    values are replaced with 9999 months to indicate 'never been requested'
    - All entries with missing TLSatCnt and TLSatPct are filled with 0, due to
    the fact that without a count you can't calculate percentage
    - All other entries with a missing value are dropped
    100/3000 entries in total are dropped
    - Drops all columns with low correlation to TARGET
"""

import pandas as pd


#Read CSV
creditRaw =pd.read_csv("../credit.csv")
#Drop the low corellation columns
keepList=['ID','TLTimeFirst', 'TLCnt03', 'TLSatCnt', 'TLDel60Cnt',
          'TL75UtilCnt','TLBalHCPct', 'TLSatPct','TLDel3060Cnt24',
          'TLOpen24Pct']
removeList=list(creditRaw)
for i in keepList:
    removeList.remove(i)
creditRaw=creditRaw.drop(columns=removeList)
#Imputate InqTimeLast, TLSatCnt, TLSatPct
#fillDict = {'InqTimeLast': 9999, "TLSatPct": 0, "TLSatCnt": 0}
#creditRaw=creditRaw.fillna(value=fillDict)
#Drop all other observation with a null value
creditRaw=creditRaw.dropna();
#Write to a csv file
creditRaw.to_csv("credit_DB.csv",index=False,sep=',')

