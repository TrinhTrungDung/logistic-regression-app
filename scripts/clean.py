#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Dec  7 13:31:11 2018

@author: Hoang
Cleaning scheme:
    - The 'ID' column is dropped due to not having any relevance to the credit
    health
    - As no information on data collection was available,
    complex imputation methods are not utilized
    - All entries with missing InqTimeLast (Duration since financial
    statement was last requested) all has a inqury count of 0, all missing
    values are replaced with 9999 months to indicate 'never been requested'
    - All entries with missing TLSatCnt and TLSatPct are filled with 0, due to
    the fact that without a count you can't calculate percentage
    - All other entries with a missing value are dropped
    100/3000 entries in total are dropped
"""

import pandas as pd


#Read CSV
creditRaw =pd.read_csv("../credit.csv")
#Drop the ID collumn
creditRaw=creditRaw.drop(columns=["ID"])
#Imputate InqTimeLast, TLSatCnt, TLSatPct
fillDict = {'InqTimeLast': 9999, "TLSatPct": 0, "TLSatCnt": 0}
creditRaw=creditRaw.fillna(value=fillDict)
#Drop all other observation with a null value
creditRaw=creditRaw.dropna();
#Write to a csv file
creditRaw.to_csv("credit_cleaned.csv",index=False,sep=',')
