import ViewPager from '@react-native-community/viewpager';
import React, { useRef, useState } from 'react';
import { ProgressBarAndroid, ProgressViewIOS, StyleSheet, Text, View } from 'react-native';
import Separator from "../../components/Separator";
import Back from "../../components/stepper/buttons/Back";
import Next from "../../components/stepper/buttons/Next";
import Submit from "../../components/stepper/buttons/Submit";
import { checkupApi, resultsApi } from '../../constants/AppSettings';
import Http from '../../services/Http';
import { getUUIDs } from '../../utils/helpers';
import Introduction from './Introduction';
import AgeQuestion from './questions/Age';
import CheckupGender from './questions/CheckupGender';
import CheckupResult from './questions/CheckupResult';
import CheckupWho from './questions/CheckupWho';
import HealthHistory from './questions/HealthHistory';
import HeightQuestion from './questions/Height';
import HowLongSinceSymptoms from './questions/HowLongSinceSymptoms';
import SymptomBodyPain from './questions/SymptomBodyPain.js';
import SymptomBreathlessNess from './questions/SymptomBreathlessNess.js';
import SymptomCough from './questions/SymptomCough.js';
import SymptomFever from './questions/SymptomFever.js';
import SymptomSoreThroat from './questions/SymptomSoreThroat.js';
import Thankyou from './questions/Thankyou';
import WeightQuestion from './questions/Weight';
import Terms from './Terms';
import Loading from './questions/Loading'

const formInitValues = {
  policyRead: false,
  gender: 'male',
  age: 30,
  height: 160,
  weight: 80,
  diabetes: false,
  kidney: false,
  heart: false,
  lungs: false,
  stroke: false,
  hypertension: false,
  hiv: false,
  transplant: false,
  smokes: false,
  fever: 0,
  cough: 0,
  breathlessness: false,
  breathlessness_type: 0,
  fatigue: false,
  joint_pain: false,
  loss_of_taste_and_smell: false,
  sore_throat: false,
  nasal_congestion: false,
  headache: false,
  chills: false,
  nausea_or_vomiting: false,
  diarrhea: false,
  conjunctival_congestion: false,
  symptoms_improvement: 0
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
      // { name: 'healthHistory', value: formInitValues.healthHistory },
    ],
    component: HealthHistory
  },
  // Fever Screen
  {
    questions: [
      // { name: 'feverSymp', value: formInitValues.feverSymp },
    ],
    component: SymptomFever
  },
  {
    questions: [
      // { name: 'coughSymp', value: formInitValues.coughSymp },
    ],
    component: SymptomCough
  },
  {
    questions: [
      // { name: 'breathlessSymp', value: formInitValues.breathlessSymp },
    ],
    component: SymptomBreathlessNess
  },
  {
    questions: [
      // { name: 'soreThroatSymp', value: formInitValues.soreThroatSymp },
    ],
    component: SymptomSoreThroat
  },
  {
    questions: [
      // { name: 'bodyPainSymp', value: formInitValues.bodyPainSymp },
    ],
    component: SymptomBodyPain
  },
  // TODO: how long will be shown based on answers to previous questions (fever, cough and headache)
  {
    questions: [
      // { name: 'howLongSymptomsFever', value: formInitValues.howLongSymptomsFever },
      // { name: 'hwoLongSymptomsCough', value: formInitValues.howLongSymptomsCough },
      // { name: 'howLongSymptomsHeadache', value: formInitValues.howLongSymptomsHeadache },
      // { name: 'howLongSymptomsProgression', value: formInitValues.howLongSymptomsProgression },
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
  let [result, setResult] = useState({});

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
    console.log(i, total)
    
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
    const UUIDs = await getUUIDs()
    const medicalUUID = UUIDs.medicalUUID;
    formValues['med_uuid'] = medicalUUID;
    let data = 0;

    try {
      console.log('submitting', formValues);
      let response = await Http.post(checkupApi, formValues)
      console.log('meduid', response.data.med_uuid)
      
      if (response.data.med_uuid) {
        let resultResponse = await Http.get(resultsApi + '/' + response.data.med_uuid);
        setResult(resultResponse.data);
      }
    } catch (res) {
      // TODO: show erro info
      console.log('error in submitting', res)
      setResult(10);
    }

    goToNextStep();
    setLoading(false);
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
    } else if (values && typeof values === 'object') {
      setFormValues(Object.assign({}, formValues, values))
    }
  }
  //#endregion

  let progress = (currentIndex + 1) / (total)
  let displayNext = currentIndex < total - 2;
  let displaySubmit = currentIndex === total - 2;
  let displayPrevious = currentIndex > 0;
  let displayDone = currentIndex === total - 1;
  let isNextDisabled = false;
  let currentComponent = screens[currentIndex];

  if (currentComponent.id === 'Terms' && formValues['policyRead'] === false) {
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
          <ViewPager
            onPageSelected={onPageSelected}
            style={{ flex: 1 }} animationsAreEnabled={true}
            initialPage={0} ref={viewPager} scrollEnabled={false}>
            {screens.map((q, k) => {
              let QScreen = isLoading ? Loading : q.component;
              return <View key={k} style={{ flex: 1 }}>
                <QScreen result={result} questions={q.questions} setValues={setValues} ></QScreen>
              </View>
            })}
          </ViewPager>
        </View>
        <Separator />
        <View style={styles.navigation}>
          <View style={styles.back}>
            {displayPrevious && !displayDone ? <Back
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
           {displayDone ? <View>
            <Submit label="Done" onSubmit={() => { }} />
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
