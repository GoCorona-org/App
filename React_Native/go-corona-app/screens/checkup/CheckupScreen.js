import React, { useRef, useState } from 'react';
import { StyleSheet, Alert, ProgressBarAndroid, ProgressViewIOS, Text, View, Image, ScrollView, ActivityIndicator } from 'react-native';

import Back from "../../components/stepper/buttons/Back";
import Next from "../../components/stepper/buttons/Next";
import Submit from "../../components/stepper/buttons/Submit";
import Separator from "../../components/Separator";
import Introduction from './Introduction';
import Terms from './Terms';
import HeightQuestion from './questions/Height';
import AgeQuestion from './questions/Age';
import WeightQuestion from './questions/Weight';
import Thankyou from './questions/Thankyou';
import CheckupResult from './questions/CheckupResult';
import ViewPager from '@react-native-community/viewpager';

import CheckupWho from './questions/CheckupWho'
import CheckupGender from './questions/CheckupGender'
import HowLongSinceSymptoms from './questions/HowLongSinceSymptoms';
import HealthHistory from './questions/HealthHistory';
import SymptomBreathlessNess from './questions/SymptomBreathlessNess.js';
import SymptomSoreThroat from './questions/SymptomSoreThroat.js';
import SymptomBodyPain from './questions/SymptomBodyPain.js';
import SymptomCough from './questions/SymptomCough.js';
import Http from '../../services/Http';
import { checkup } from '../../constants/AppSettings';

const formInitValues = {
  policyRead: false,
  checkupfor: null,
  gender: null,
  healthHistory: null,
  breathlessSymp: null,
  soreThroatSymp: null,
  bodyPainSymp: null,
  age: 30,
  height: 160,
  weight: 68,
  howLongSymptomsFever: 0,
  howLongSymptomsCough: 0,
  howLongSymptomsHeadache: 0,
  howLongSymtomsProgression: "noChange"
}

const screens = [
  {
    id: 'Introduction',
    title: 'Introduction',
    component: Introduction
  },
  {
    id: 'Terms',
    title: 'Introduction',
    component: Terms,
    questions: [
      { name: 'policyRead', value: formInitValues.policyRead },
    ]
  },
  {
    component: CheckupWho,
    questions: [
      { name: 'checkupfor', value: formInitValues.checkupfor },
    ]
  },
  {
    component: CheckupGender,
    questions: [
      { name: 'gender', value: formInitValues.gender },
    ]
  },
  {
    questions: [
      { name: 'age', value: formInitValues.age },
    ],
    component: AgeQuestion
  },
  {
    questions: [
      { name: 'height', value: formInitValues.height },
    ],
    component: HeightQuestion
  },
  {
    questions: [
      { name: 'weight', value: formInitValues.weight },
    ],
    component: WeightQuestion
  },
  {
    questions: [
      { name: 'healthHistory', value: formInitValues.healthHistory },
    ],
    component: HealthHistory
  },
  // Fever Screen
  {
    questions: [
      { name: 'coughSymp', value: formInitValues.coughSymp },
    ],
    component: SymptomCough
  },
  {
    questions: [
      { name: 'breathlessSymp', value: formInitValues.breathlessSymp },
    ],
    component: SymptomBreathlessNess
  },
  {
    questions: [
      { name: 'soreThroatSymp', value: formInitValues.soreThroatSymp },
    ],
    component: SymptomSoreThroat
  },
  {
    questions: [
      { name: 'bodyPainSymp', value: formInitValues.bodyPainSymp },
    ],
    component: SymptomBodyPain
  },
  // TODO: how long will be shown based on answers to previous questions (fever, cough and headache)
  {
    questions: [
      { name: 'howLongSymptomsFever', value: formInitValues.howLongSymptomsFever },
      { name: 'hwoLongSymptomsCough', value: formInitValues.howLongSymptomsCough },
      { name: 'howLongSymptomsHeadache', value: formInitValues.howLongSymptomsHeadache },
      { name: 'howLongSymptomsProgression', value: formInitValues.howLongSymptomsProgression },
    ],
    component: HowLongSinceSymptoms
  },
  {
    component: Thankyou
  },
  {
    title: 'Result',
    component: CheckupResult
  }
]



export default function CheckupScreen() {
  const viewPager = useRef(null);
  let [currentIndex, setCurrentIndex] = useState(0);
  let [isLoading, setLoading] = useState(false);
  let [result, setResult] = useState(null);

  let [formValues, setFormValues] = useState(formInitValues);

  let total = screens.length;
  let title = screens[currentIndex].title || 'Patient';

  //#region page selection
  const goToPreviousStep = () => {
    var i = currentIndex - 1;
    if (i >= 0) {
      setCurrentIndex(i)
      viewPager.current.setPage(i);
    }
  }
  const goToNextStep = () => {
    let i = currentIndex + 1;
    if (i <= total - 1) {
      setCurrentIndex(i)
      viewPager.current.setPage(i);
    }
  }
  const onPageSelected = (e) => {
    // const pos = e.nativeEvent.position;
    // setTimeout(() => {
    //   setCurrentIndex(pos)
    // }, 500);
  }

  // TODO: submit all values
  const submitForm = async () => {
    setLoading(true);
    let data = 0;
    try {
      let data = await Http.post(checkup, formValues)
      console.log('data', data);
      setResult(data);
    } catch (res) {
      // TODO: show erro info
      console.log('error in submitting', res)
      setResult(10);
    }
    setLoading(false);
    goToNextStep();
  }
  //#endregion page selection

  //#region values setting
  const setValues = (values) => {
    if (values && values.length) {
      values.forEach((v) => {
        if (v.value !== undefined) {
          formValues[v.name] = v.value;
          setFormValues(Object.assign({}, formValues));
        }
      })
    }
  }
  //#endregion

  let progress = (currentIndex + 1) / (total)
  let displayNext = currentIndex < total - 2;
  let displaySubmit = currentIndex === total - 2;
  let displayPrevious = currentIndex > 0;
  let isNextDisabled = false;
  let currentComponent = screens[currentIndex];
  if (currentComponent.id === 'Terms' && formValues['policyRead'] === false) {
    isNextDisabled = true
  }

  return (
    isLoading ? <ActivityIndicator>
    </ActivityIndicator> :
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
          <ViewPager
            onPageSelected={onPageSelected}
            style={{ flex: 1 }} animationsAreEnabled={true}
            initialPage={0} ref={viewPager} scrollEnabled={false}>
            {screens.map((q, k) => {
              let QScreen = q.component;
              return <View key={k} style={{ flex: 1 }}>
                <QScreen result={result} questions={q.questions} setValues={setValues} ></QScreen>
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
            <Submit onSubmit={() => { submitForm() }} />
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
