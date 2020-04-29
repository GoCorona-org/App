import React, { useRef, useState } from 'react';
import { StyleSheet, Alert, ProgressBarAndroid, ProgressViewIOS, Text, View, Image, ScrollView } from 'react-native';
import ViewPager from '@react-native-community/viewpager';
import { useNavigation } from '@react-navigation/native';

import Back from "../../../components/stepper/buttons/Back";
import Next from "../../../components/stepper/buttons/Next";
import Submit from "../../../components/stepper/buttons/Submit";
import Separator from "../../../components/Separator";

import International from './questions/International';
import Domestic from './questions/Domestic';
import Lockdown from './questions/Lockdown'
import Thankyou from './questions/Thankyou'

import Http from '../../../services/Http'
import { travelApi } from '../../../constants/AppSettings'
import { getUUIDs } from '../../../utils/helpers';

const formInitValues = {
  international_mode: false,
  visitedCountries: [], // only one country supported now
  domesticTravel: false, // ommited in API
  domestic_airport_from: null,
  domestic_airport_to: null,
  hometown: null, // ommited in API
  current_state: null,
  domestic_flight: false,
  domestic_train: false,
  domestic_auto: false,
  domestic_cab: false
}

const screens = [
  {
    id: "International",
    title: 'International',
    component: International,
    questions: [
      { name: 'international_mode', value: formInitValues.international_mode },
      { name: 'visitedCountries', value: formInitValues.visitedCountries },
    ]
  },
  {
    id: "Domestic",
    title: "Domestic",
    component: Domestic,
    questions: [
      { name: 'domesticTravel', value: formInitValues.domesticTravel },
      { name: 'domestic_airport_from', value: formInitValues.domestic_airport_from },
      { name: 'domestic_airport_to', value: formInitValues.domestic_airport_to }
    ]
  },
  {
    id: "Lockdown",
    title: "Lockdown",
    component: Lockdown,
    questions: [
      { name: 'hometown', value: formInitValues.hometown },
      { name: 'current_state', value: formInitValues.current_state },
      { name: 'domestic_flight', value: formInitValues.domestic_flight },
      { name: 'domestic_train', value: formInitValues.domestic_train },
      { name: 'domestic_auto', value: formInitValues.domestic_auto },
      { name: 'domestic_cab', value: formInitValues.domestic_cab }
    ]
  },
  {
    component: Thankyou
  }
]


export default function TravelScreen() {
  const viewPager = useRef(null);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [formValues, setFormValues] = useState(formInitValues);
  const navigation = useNavigation()

  const total = screens.length;
  const title = screens[currentIndex].title || 'Travel';

  const goToPreviousStep = () => {
    var i = currentIndex - 1;
    if (i >= 0) {
      viewPager.current.setPage(i);
      setCurrentIndex(i)
    }
  }

  // TODO: submit all values
  const submitForm = async () => {
    let data = 0;
    try {

      // temporarily
      const modifiedFormValues = { ...formValues, country_travelled: formValues.visitedCountries.length ? formValues.visitedCountries[0] : "" }
      console.log(modifiedFormValues)
      const UUIDs = await getUUIDs()
      let response = await Http.put(`${travelApi}/${UUIDs.medicalUUID}`, modifiedFormValues)
      console.log('response data', response.data);
    } catch (res) {
      console.log('error in submitting', res)
    }
    navigation.goBack()
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
  let currentComponent = screens[currentIndex];

  if (currentComponent.id === "International" && formValues['international_mode'] === "yes" && formValues['visitedCountries'].length === 0) {
    isNextDisabled = true
  }

  if (currentComponent.id === "Domestic" && formValues['domesticTravel'] === "yes" && (!formValues['domestic_airport_from'] || !formValues['domestic_airport_to'])) {
    isNextDisabled = true
  }

  if (currentComponent.id === "Lockdown" && (!formValues['hometown'] || !formValues['current_state'])) {
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
              <QScreen questions={q.questions} setValues={setValues} ></QScreen>
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
          <Submit onSubmit={() => submitForm()} />
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
