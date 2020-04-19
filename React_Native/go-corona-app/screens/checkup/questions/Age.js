import React, { useEffect, useState } from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { Slider } from 'react-native-elements';
import PlusImg from '../../../assets/images/Plus';
import MinusImg from '../../../assets/images/Minus';
import SvgIcon from '../../../components/SvgIcon';

const AgeQuestion = (props) => {
  const [age, setAge] = useState(30);
  const increase = () => {
    var val = age + 1;
    setValue(val);
  }
  const decrease = () => {
    var val = age - 1;
    setValue(val);
  }
  const setValue = (val) => {
    setAge(val);
    const name = props.questions && props.questions[0].name;
    props.setValues && props.setValues([{ name, value: val }]);
  }
  return (<View style={styles.container}>
    <Text style={styles.question}>How old are you?</Text>
    <View style={{ flex: 1, paddingTop: 50, alignItems: 'center', justifyContent: 'flex-start' }}>
      <Text style={styles.value}>{age} <Text style={styles.mini}>yrs</Text></Text>
      <View style={{ paddingTop: 20, paddingBottom: 40, alignItems: 'center', justifyContent: 'flex-start' }}>
        <Slider
          style={styles.slider}
          value={age}
          onValueChange={setValue}
          step={1}
          minimumTrackTintColor={'#49D581'}
          maximumTrackTintColor={'#CBCBCB'}
          minimumValue={1}
          maximumValue={100}
          thumbTintColor={'white'}
          thumbStyle={styles.thumb}
          thumbTouchSize={{ width: 60, height: 60 }}
        />
      </View>
      <View style={styles.bContainer}>
        <SvgIcon onPress={decrease} size={40} svg={MinusImg}>
        </SvgIcon>
        <SvgIcon onPress={increase} size={40} svg={PlusImg}>
        </SvgIcon>
      </View>
    </View>
  </View>)
}

const styles = StyleSheet.create({
  bContainer: {
    flexDirection: "row",
    width: 140,
    paddingBottom: 40,
    justifyContent: 'space-around'
  },
  container: {
    flex: 1,
    padding: 30,
  },
  question: {
    fontSize: 16,
    fontWeight: "bold"
  },
  value: {
    fontSize: 20,
    fontWeight: "bold"
  },
  slider: {
    width: 250
  },
  thumb: {
    padding: 15,
    elevation: 3,
    borderRadius: 50
  },
  mini: {
    fontSize: 12,
  }
})

export default AgeQuestion