import React, { useEffect, useState } from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { Slider } from 'react-native-elements';
import WeightImg from '../../../assets/images/Weight';
import PlusImg from '../../../assets/images/Plus';
import MinusImg from '../../../assets/images/Minus';
import SvgIcon from '../../../components/SvgIcon';

const WeightQuestion = (props) => {
  const [weight, setWeight] = useState(60);
  const increase = () => {
    var val = weight + 1;
    setValue(val);
  }
  const decrease = () => {
    var val = weight - 1;
    setValue(val);
  }
  const setValue = (val) => {
    setWeight(val);
    const name = props.questions && props.questions[0].name;
    props.setValues && props.setValues([{ name, value: val }]);
  }
  return (<View style={styles.container}>
    <Text style={styles.question}>What is your weight?</Text>
    <View style={{ paddingTop: 50, alignItems: 'center', justifyContent: 'center' }}>
      <Text style={styles.value}>{weight} <Text style={styles.mini}>Kgs</Text></Text>
      <View style={{ paddingTop: 20, paddingBottom: 40, alignItems: 'center', justifyContent: 'flex-start' }}>
        <Slider
          style={styles.slider}
          value={weight}
          onValueChange={setValue}
          step={1}
          minimumTrackTintColor={'#49D581'}
          maximumTrackTintColor={'#CBCBCB'}
          minimumValue={3}
          maximumValue={300}
          thumbTintColor={'white'}
          thumbStyle={styles.thumb}
          thumbTouchSize={{ width: 60, height: 60 }}
        />
      </View>
    </View>
    <View style={styles.bContainer}>
      <SvgIcon onPress={decrease} size={40} svg={MinusImg}>
      </SvgIcon>
      <SvgIcon onPress={increase} size={40} svg={PlusImg}>
      </SvgIcon>
    </View>

    <WeightImg width={150} height={150}></WeightImg>
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
    justifyContent: 'flex-start',
    alignItems: 'center'
  },
  question: {
    fontSize: 16,
    alignSelf: 'flex-start',
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

export default WeightQuestion