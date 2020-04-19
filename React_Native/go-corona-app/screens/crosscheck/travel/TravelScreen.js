import React, { useRef, useState } from 'react';
import { StyleSheet, Alert, ProgressBarAndroid, ProgressViewIOS, Text, View, Image, ScrollView } from 'react-native';
import ViewPager from '@react-native-community/viewpager';

import Back from "../../../components/stepper/buttons/Back";
import Next from "../../../components/stepper/buttons/Next";
import Submit from "../../../components/stepper/buttons/Submit";
import Separator from "../../../components/Separator";

import International from './questions/International';
import Domestic from './questions/Domestic';
import Lockdown from './questions/Lockdown'

const formInitValues = {
  internationTravel: "no",
  visitedCountries: [],
  domesticTravel: false,
  airportsVisited: [],
  hometown: null,
  currentLocation: null,
  modesOfTransport: []
}

const screens = [
  {
    title: 'Travel',
    component: International,
    questions: [
      { name: 'internationTravel', value: formInitValues.internationTravel },
      { name: 'visitedCountries', value: formInitValues.visitedCountries },
    ]
  },
  {
    title: 'Travel',
    component: Domestic,
    questions: [
      { name: 'domesticTravel', value: formInitValues.domesticTravel },
      { name: 'airportsVisisted', value: formInitValues.airportsVisited }
    ]
  },
  {
    title: 'Lockdown',
    component: Lockdown,
    questions: [
      { name: 'hometown', value: formInitValues.hometown },
      { name: 'currentLocation', value: formInitValues.currentLocation },
      { name: 'modesOfTransport', value: formInitValues.modesOfTransport }
    ]
  }
]


export default function TravelScreen() {
  const viewPager = useRef(null);
  let [currentIndex, setCurrentIndex] = useState(0);
  let [formValues, setFormValues] = useState(formInitValues);

  let total = screens.length;
  let title = screens[currentIndex].title || 'Travel';
  const goToPreviousStep = () => {
    var i = currentIndex - 1;
    if (i >= 0) {
      viewPager.current.setPage(i);
      setCurrentIndex(i)
    }
  }

  const convertToObject = (questionsArray)=>{
    var o = {} ;
    questionsArray.forEach((q)=>o[q.name]=q.value);
    return o;
  }
  const setValues = (values) => {
    if (values && values.length) {
      values.forEach((v) => {
        formValues[v.name] = v.value;
        setFormValues(Object.assign({}, formValues));
      })
    }
  }
  const goToNextStep = () => {
    var i = currentIndex + 1;
    if (i <= total - 1) {
      viewPager.current.setPage(i);
      setTimeout(() => {
        setCurrentIndex(i);
      }, 100);
    }
  }
  let progress = (currentIndex + 1) / (total)
  let displayNext = currentIndex < total - 1;
  let displaySubmit = currentIndex === total - 1;
  let displayPrevious = currentIndex > 0;
  let isNextDisabled = false;
  if (currentIndex === 1 && formValues['policyRead'] === false) {
    isNextDisabled = true
  }
  return (
    <View style={styles.container}>
      <View style={styles.progressContainer}>
        <Text>{title}</Text>
        {
          (Platform.OS === 'android')
            ?
            <ProgressBarAndroid progress={progress} styleAttr="Horizontal" indeterminate={false} style={styles.progressAnd} />
            :
            <ProgressViewIOS progress={progress} style={styles.progressIos} progressTintColor="#49D581" />
        }
      </View>
      <View style={styles.content}>
        <ViewPager style={{ flex: 1 }} initialPage={0} ref={viewPager} scrollEnabled={false}>
          {screens.map((q, k) => {
            let QScreen = q.component;
            return <View key={k} style={{ flex: 1 }}>
              <QScreen questions={convertToObject(q.questions)} setValues={setValues} ></QScreen>
            </View>
          })}
        </ViewPager>
      </View>
      <Separator />
      <View style={styles.navigation}>
        <View style={styles.back}>
          {displayPrevious ? <Back
            isActive={true}
            goToPreviousStep={() => goToPreviousStep()}
          /> : null}
        </View>
        {displayNext ? <View style={styles.next}>
          <Next disabled={isNextDisabled}
            goToNextStep={() => goToNextStep()}
          />
        </View> : null}
        {displaySubmit ? <View>
          <Submit onSubmit={() => { goToNextStep(); Alert.alert(JSON.stringify([formValues.internationTravel, formValues.domesticTravel, formValues.hometown, formValues.currentLocation])) }} />
        </View> : null}
      </View>
    </View>
  );

  
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fafafa',
    justifyContent: 'flex-start'
  },
  progressContainer: {
    height: 40,
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: "center",
    backgroundColor: '#EEEEEE',
    paddingHorizontal: 30
  },
  content: {
    flex: 1,
  },
  navigation: {
    height: '10%',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    marginLeft: 20,
    marginRight: 20,
  },
  progressAnd: {
    width: 100,
    color: '#49D581',
  },
  progressIos: {
    width: 100,
  }
});
