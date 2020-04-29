import React, { useState, useEffect, Fragment } from 'react';
import { StyleSheet, Text, View, ScrollView } from 'react-native';
import { Divider, RadioButton } from 'react-native-paper';
import BreathlessnessImage from '../../../assets/images/Breathlessness.svg';


export default function SymptomBreathlessNess({ setValues }) {
  const [breathlessness, setBreathlessness] = useState('false')
  const [breathlessnessType, setBreathlessnessType] = useState('0')

  const setBreathlessnessValue = (v) => {
    setValues({ breathlessness: v == 'true' });
    setBreathlessness(v);
  }

  useEffect(() => {
    setValues({ breathlessness_type: +breathlessnessType })
  }, [breathlessnessType])

  useEffect(() => {
    setValues({ breathlessness_type: breathlessness === 'true' ? +breathlessnessType : 0 })
  }, [breathlessness])

  const symptoms = [
    {
      name: 'At Night',
      value: "1"
    },
    {
      name: 'While lying flat on bed',
      value: "2"
    },
    {
      name: 'Breathlessness that comes and goes',
      value: "3"
    },
    {
      name: 'Breathlessness while exercising',
      value: "4"
    },
    {
      name: 'Sudden feeling of breathlessness',
      value: "5"
    },
  ];


  return (
    <ScrollView>
      <View style={styles.viewContainer}>
        <View style={styles.sectionContainer}>
          <Text style={styles.title}>Please tell us about your symptoms</Text>
          <BreathlessnessImage style={styles.image} width="200" height="120" />
        </View>
        <Text style={styles.textSty}>Are you feeling breathless?</Text>
        <View style={styles.agreeContainer}>
          <RadioButton.Group
            onValueChange={values => setBreathlessnessValue(values)}
            value={breathlessness}
          >
            <View style={styles.agreeContainer}>
              <Divider />
              <View style={styles.radAlign}>
                <RadioButton.Android value="false" color="#E03D51" uncheckedColor="#D2D2D2" />
                <Text style={styles.radTxt}>No</Text>
              </View>
              <Divider />
            </View>
            <View style={styles.agreeContainer}>
              <View style={styles.radAlign}>
                <RadioButton.Android value="true" color="#E03D51" uncheckedColor="#D2D2D2" />
                <Text style={styles.radTxt}>Yes</Text>
              </View>
              <Divider />
            </View>
          </RadioButton.Group>
        </View>
        {
          breathlessness === 'true' ?
            <Fragment>
              <Text style={styles.textSty}>Can you describe your breathlessness?</Text>
              <View style={styles.agreeContainer}>
                <RadioButton.Group
                  onValueChange={breathlessnessType => setBreathlessnessType(breathlessnessType)}
                  value={breathlessnessType}
                >
                  {symptoms.map((item) => (
                    <View style={styles.agreeContainer} key={item.value}>
                      <View style={styles.radAlign} key={item.value}>
                        <RadioButton.Android value={item.value} color="#E03D51" uncheckedColor="#D2D2D2" />
                        <Text style={styles.radTxt}>{item.name}</Text>
                      </View>
                      <Divider />
                    </View>
                  )
                  )}
                </RadioButton.Group>
              </View>
            </Fragment> : null}
      </View>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  viewContainer: {
    flex: 1,
    backgroundColor: '#fafafa',
    justifyContent: 'flex-start'
  },
  container: {
    justifyContent: 'center'
  },
  contStyle: {
    borderWidth: 0
  },
  sectionContainer: {
    marginTop: 40,
    marginBottom: 5,
    alignItems: 'center',
  },
  title: {
    fontSize: 18,
    fontWeight: "bold",
    marginLeft: 10,
    marginBottom: 25
  },
  helpLinks: {
    marginTop: 30,
    padding: 5,
    width: "90%"
  },
  subTitle: {
    alignSelf: 'flex-start',
    marginLeft: 25,
  },
  agreeContainer: {
    marginLeft: 10,
    marginRight: 10,
    flexDirection: 'column'
  },
  txtStyle: {
    fontWeight: 'normal'
  },
  textSty: {
    marginLeft: 24,
    marginBottom: 20,
    marginTop: 40
  },
  radAlign: {
    flexDirection: 'row'
  },
  radTxt: {
    marginTop: 8
  },
  image: {
    alignSelf: 'center',
    marginTop: 10
  }
});
