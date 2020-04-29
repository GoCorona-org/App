import React, { useState } from 'react';
import { ScrollView, StyleSheet, Text, View } from 'react-native';
import { CheckBox } from 'react-native-elements';
import { Divider, RadioButton } from 'react-native-paper';

const questions = [
  { name: 'asthma', value: false, label: 'Asthma' },
  { name: 'hypertension', value: false, label: 'High BP' },
  { name: 'kidney', value: false, label: 'Kidney disease' },
  { name: 'heart', value: false, label: 'Heart disease' },
  { name: 'lungs', value: false, label: 'Lung disease' },
  { name: 'stroke', value: false, label: 'Stroke' },
  { name: 'diabetes', value: false, label: 'Diabetes' },
  { name: 'none', value: false, label: 'No, I do not have any of the above health issues' }
]

const initialValues = questions.reduce((s, c) => {
  s[c.name] = c.value;
  return s;
}, {})

export default function HealthHistory({ setValues}) {
  const [checkedItems, setCheckedItems] = useState(initialValues);
  const [smokes, setSmokes] = useState('false')
  const [transplant, setTransplant] = useState('false')

  const handleChange = (item) => {
    let updatedItems = {}
    
    if (item.name === "none") {
      updatedItems = { ...initialValues, 'none': !checkedItems[item.name] }
    } else {
      updatedItems = { ...checkedItems, [item.name]: !checkedItems[item.name] };
    }

    setCheckedItems(updatedItems);
    setValues(updatedItems);
  }

  const setSmokesValue = (value) => {
    setValues({ 'smokes': value === 'true' });
    setSmokes(value);
  }

  const setTransplantValue = (value) => {
    setValues({ 'transplant': value === 'true', 'hiv': value === 'true' });
    setTransplant(value);
  }

  return (
    <ScrollView>
      <View style={styles.viewContainer}>
        <View style={styles.sectionContainer}>
          <Text style={styles.title}>Please check all the statements below that apply to you</Text>
        </View>
        <Text style={{...styles.textSty, marginTop: 0}}>Do you have had a health history?</Text>
        <Divider />
        <View style={styles.agreeContainer}>
          {questions.map((item) => (
            <View key={item.name}>
              <CheckBox containerStyle={styles.contStyle} textStyle={styles.txtStyle} title={item.label} checked={checkedItems[item.name]} onPress={() => handleChange(item)} uncheckedColor='#D2D2D2' checkedColor='#E03D51' />
              <Divider />
            </View>
          )
          )}
        </View>
        <Text style={styles.textSty}>Do you smoke cigarettes?</Text>
        <Divider />
        <View style={styles.agreeContainer}>
          <RadioButton.Group
            onValueChange={value => setSmokesValue(value)}
            value={smokes}
          >
             <View style={styles.agreeContainer}>
              <View style={styles.radAlign}>
                <RadioButton.Android value={'false'} color="#E03D51" uncheckedColor="#D2D2D2" />
                <Text style={styles.radTxt}>No</Text>
              </View>
              <Divider />
            </View>
            <View style={styles.agreeContainer}>
              <View style={styles.radAlign}>
                <RadioButton.Android value={'true'} color="#E03D51" uncheckedColor="#D2D2D2" />
                <Text style={styles.radTxt}>Yes</Text>
              </View>
              <Divider />
            </View>
          </RadioButton.Group>
        </View>
        <Text style={styles.textSty}>Do you have low immunity due to organ transplant or HIV?</Text>
        <View style={styles.agreeContainer}>
          <RadioButton.Group
            onValueChange={valueNext => setTransplantValue(valueNext)}
            value={transplant}
          >
          <View style={styles.agreeContainer}>
              <View style={styles.radAlign}>
                <RadioButton.Android value={'false'} color="#E03D51" uncheckedColor="#D2D2D2" />
                <Text style={styles.radTxt}>No</Text>
              </View>
              <Divider />
            </View>
            <View style={styles.agreeContainer}>
              <View style={styles.radAlign}>
                <RadioButton.Android value={'true'} color="#E03D51" uncheckedColor="#D2D2D2" />
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
  }
});
