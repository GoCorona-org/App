import React, { useState, useEffect, Fragment } from 'react';
import { StyleSheet, Text, View, ScrollView } from 'react-native';
import { Divider, RadioButton } from 'react-native-paper';
import Congestion from '../../../assets/images/Congestion.svg';


export default function SymptomFever({ setValues }) {
  const [fever, setFever] = useState('false')
  const [feverType, setFeverType] = useState("0")

  const setFeverValue = (v) => {
    setFever(v);
  }

  useEffect(() => {
    setValues({ fever: +feverType })
  }, [feverType])

  useEffect(() => {
    setValues({ fever: fever === 'true' ? +feverType : 0 })
  }, [fever])

  return (
    <ScrollView>
      <View style={styles.viewContainer}>
        <View style={styles.sectionContainer}>
          <Text style={styles.title}>Please tell us about your symptoms</Text>
          <Congestion style={styles.image} width="200" height="120" />
        </View>
        <Text style={styles.textSty}>Do you have fever?</Text>
        <View style={styles.agreeContainer}>
          <RadioButton.Group
            onValueChange={values => setFeverValue(values)}
            value={fever}
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
        {fever === 'true' ?
          <Fragment>
            <Text style={styles.textSty}>Can you describe your fever?</Text>
            <View style={styles.agreeContainer}>
              <RadioButton.Group
                onValueChange={feverType => setFeverType(feverType)}
                value={feverType}
              >
                <View style={styles.agreeContainer}>
                  <View style={styles.radAlign}>
                    <RadioButton.Android value="1" color="#E03D51" uncheckedColor="#D2D2D2" />
                    <Text style={styles.radTxt}>Temperature between 98.6 to 100.4 °C</Text>
                  </View>
                  <Divider />
                </View>
                <View style={styles.agreeContainer}>
                  <View style={styles.radAlign}>
                    <RadioButton.Android value="2" color="#E03D51" uncheckedColor="#D2D2D2" />
                    <Text style={styles.radTxt}>Temperature between 100.4 to 104 °C</Text>
                  </View>
                  <Divider />
                </View>
                <View style={styles.agreeContainer}>
                  <View style={styles.radAlign}>
                    <RadioButton.Android value="3" color="#E03D51" uncheckedColor="#D2D2D2" />
                    <Text style={styles.radTxt}>Fever that comes and goes</Text>
                  </View>
                  <Divider />
                </View>
                <View style={styles.agreeContainer}>
                  <View style={styles.radAlign}>
                    <RadioButton.Android value="4" color="#E03D51" uncheckedColor="#D2D2D2" />
                    <Text style={styles.radTxt}>Not checked the temperature</Text>
                  </View>
                  <Divider />
                </View>
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
