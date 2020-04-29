import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, View, ScrollView } from 'react-native';
import { Divider, RadioButton } from 'react-native-paper';

import Congestion from '../../../assets/images/Congestion.svg';

export default function SymptomSoreThroat({ setValues }) {
  const [soreThroat, setSoreThroat] = useState('false')
  const [nasalCongestion, setNasalCongestion] = useState('false')
  const [tasteOrSmell, setTasteOrSmell] = useState('false')

  useEffect(() => {
    setValues({ sore_throat: soreThroat === 'true' })
  }, [soreThroat])

  useEffect(() => {
    setValues({ nasal_congestion: nasalCongestion === 'true' })
  }, [nasalCongestion])

  useEffect(() => {
    setValues({ loss_of_taste_and_smell: tasteOrSmell === 'true' })
  }, [tasteOrSmell])

  return (
    <ScrollView>
      <View style={styles.viewContainer}>
        <View style={styles.sectionContainer}>
          <Text style={styles.title}>Please tell us about your symptoms</Text>
          <Congestion style={styles.image} width="200" height="120" />
        </View>
        <Text style={styles.textSty}>Do you have a sore throat?</Text>
        <Divider />
        <View style={styles.agreeContainer}>
          <RadioButton.Group
            onValueChange={values => setSoreThroat(values)}
            value={soreThroat}
          >
            <View style={styles.agreeContainer}>
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
        <Text style={styles.textSty}>Do you have nasal congestion?</Text>
        <View style={styles.agreeContainer}>
          <RadioButton.Group
            onValueChange={nasalCongestion => setNasalCongestion(nasalCongestion)}
            value={nasalCongestion}
          >
            <View style={styles.agreeContainer}>
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
        <Text style={styles.textSty}>Have you lost a sense of taste or smell?</Text>
        <View style={styles.agreeContainer}>
          <RadioButton.Group
            onValueChange={tasteOrSmell => setTasteOrSmell(tasteOrSmell)}
            value={tasteOrSmell}
          >
            <View style={styles.agreeContainer}>
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
