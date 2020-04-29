import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, View, ScrollView } from 'react-native';
import { Divider, RadioButton } from 'react-native-paper';
import { CheckBox } from 'react-native-elements';
import BodyPain from '../../../assets/images/BodyPain.svg';

export default function SymptomBodyPain({ setValues }) {
  const [checkedItems, setCheckedItems] = useState({
    headache: false, chills: false, nausea_or_vomiting: false, diarrhea: false, conjunctival_congestion: false
  });

  const muscleOrJointPain = [
    {
      name: 'Muscle Pain',
      value: 'muscle_pain'
    },
    {
      name: 'Joint Pain',
      value: 'joint_pain'
    },
    {
      name: 'None',
      value: 'none'
    }
  ];

  const otherSymptoms = [
    {
      name: 'Headaches',
      key: 'headache'
    },
    {
      name: 'Chills',
      key: 'chills'
    },
    {
      name: 'Nausea or Vomiting',
      key: 'nausea_or_vomiting'
    },
    {
      name: 'Diarrhea',
      key: 'diarrhea'
    },
    {
      name: 'Conjunctival congestion',
      key: 'conjunctival_congestion'
    }
  ];

  const [muscleOrJoin, setMusleOrJoint] = useState('muscle_pain')
  const [fatigued, setFatigued] = useState('no')

  useEffect(() => {
    setValues({ joint_pain: (muscleOrJoin === 'muscle_pain' || muscleOrJoin === 'joint_pain') ? true : false})
  }, [muscleOrJoin])

  useEffect(() => {
    setValues({ fatigue: fatigued === 'yes'})
  }, [fatigued])

  const handleChange = (item) => {
    const updatedItems = { ...checkedItems, [item.key]: !checkedItems[item.key] };
    
    setCheckedItems(updatedItems);
    setValues(updatedItems);
  }

  return (
    <ScrollView>
      <View style={styles.viewContainer}>
        <View style={styles.sectionContainer}>
          <Text style={styles.title}>Do you have any other symptoms?</Text>
          <BodyPain style={styles.image} width="200" height="120" />
        </View>
        <Text style={styles.textSty}>Do you have any kind of body pain?</Text>
        <Divider />
        <View style={styles.agreeContainer}>
          <RadioButton.Group
            onValueChange={muscleOrJoin => setMusleOrJoint(muscleOrJoin)}
            value={muscleOrJoin}
          >
            {muscleOrJointPain.map((item) => (
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
        <Text style={styles.textSty}>Do you feel fatigued or drowsy?</Text>
        <View style={styles.agreeContainer}>
          <RadioButton.Group
            onValueChange={fatigued => setFatigued(fatigued)}
            value={fatigued}
          >
            <View style={styles.agreeContainer}>
              <View style={styles.radAlign}>
                <RadioButton.Android value="no" color="#E03D51" uncheckedColor="#D2D2D2" />
                <Text style={styles.radTxt}>No</Text>
              </View>
              <Divider />
            </View>
            <View style={styles.agreeContainer}>
              <View style={styles.radAlign}>
                <RadioButton.Android value="yes" color="#E03D51" uncheckedColor="#D2D2D2" />
                <Text style={styles.radTxt}>Yes</Text>
              </View>
              <Divider />
            </View>
          </RadioButton.Group>
        </View>
        <Text style={styles.textSty}>Please check if you have any of the following symptoms</Text>
        <View style={styles.agreeContainer}>
          {otherSymptoms.map((item) => (
            <View key={item.key}>
              <CheckBox key={item.key} containerStyle={styles.contStyle} textStyle={styles.txtStyle} title={item.name} checked={checkedItems[item.key]} onPress={() => handleChange(item)} uncheckedColor='#D2D2D2' checkedColor='#E03D51' />
              <Divider />
            </View>
          )
          )}
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
