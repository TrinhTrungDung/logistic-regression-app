'"
LDA implementation on the credit table.
Three assumptions of LDA in this case must have been violated:
- Normal distribution in data (Very skewed, multiple data points
  have less than 2 observation)
- Satistically independent (Some data set correlates a lot, like
  TLSatCnt and TLSatPct)
- Identical covariance matrices for every class(Most likely not
  since data like TLDel3060Cnt24 has really strange distribution)
Data also overlaps a lot in terms of distribution
-> Scatterplot might reveal terrible correlations
OLS might be better here
"'
install.packages("MASS")
library(MASS)

#Load the data set
creditData<-read.csv("~/Documents/credit_cleaned.csv",header=T,na.strings = c(""))
creditData$BanruptcyInd<-as.factor(creditData$BanruptcyInd)
creditData$TARGET<-as.factor(creditData$TARGET)
#Splitting the data set
test<-creditData[1:300,]
train<-creditData[301:2900,]
#Deleting the TARGET column on the test subset
test$TARGET<-NULL
#Output the LDA model
model <- lda(TARGET ~ ., train)
#Use the model to predict and output accuracy
fitted.results<-predict(model,newdata=test)
misClasificError<-mean(fitted.results[["class"]] != creditData[1:300,]$TARGET)
print(paste('LDA Model Accuracy: ',1-misClasificError))