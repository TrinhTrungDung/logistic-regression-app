'"
Very basic logistic regression using cleaned data set. Check out
clean.py for how to do it

@author: Hoang
"'
#install.packages("ResourceSelection","DescTools","aod","caret")
library(ResourceSelection)
library(DescTools)
library(aod)
library(caret)
#Load the data set
creditData<-read.csv("~/Documents/credit_cleaned.csv",header=T,na.strings = c(""))
creditData$BanruptcyInd<-as.factor(creditData$BanruptcyInd)
#Splitting the data set
test<-creditData[1:300,]
train<-creditData[301:2900,]
#Deleting the TARGET column on the test subset
test$TARGET<-NULL
#Training the model using the train subset
model<-glm(formula = TARGET ~ ., family = binomial(link = "logit"), 
           data = train)
#Print out the summary
#summary(model)
#Printing out variable importance
varImpTable <- as.data.frame(varImp(model))
varImpTable <- data.frame(Overall = varImpTable$Overall, names = rownames(varImpTable))
varImpTable <- varImpTable[order(varImpTable$Overall,decreasing = T),]
print(varImpTable)
#Using the model to predict TARGET on the test subset
fitted.results<-predict(model,newdata=test,type='response')
fitted.results<-ifelse(fitted.results>0.5,1,0)
misClasificError<-mean(fitted.results != creditData[1:300,]$TARGET)
print(paste('Model Accuracy: ',1-misClasificError))
#Create a new model based on significance of factor
newModel<-glm(formula = TARGET ~ TLTimeFirst + TLCnt03 + 
                TLSatCnt + TLDel60Cnt + TL75UtilCnt + 
                TLBalHCPct + TLSatPct + TLDel3060Cnt24 + 
                TLOpen24Pct, family = binomial(link="logit"),
              data=train)
print(summary(newModel))
#Output Pseudo R^2
print(PseudoR2(model,which="McFadden"))
print(PseudoR2(newModel,which="McFadden"))
#Output ANOVA 
print(anova(model,newModel, test="Chisq"))
#Output Hoslem & Lemeshow
hoslem.test(train$TARGET,fitted(newModel),g=10)
#Using the new model to predict TARGET on the test subset
fitted.results<-predict(newModel,newdata=test,type='response')
results<-ifelse(fitted.results>0.5,1,0)
misClasificError<-mean(results != creditData[1:300,]$TARGET)
print(paste('New Model Accuracy: ',1-misClasificError))
