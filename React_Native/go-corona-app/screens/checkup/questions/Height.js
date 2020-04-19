import React, { useEffect, useState } from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { Slider } from 'react-native-elements';
import PlusImg from '../../../assets/images/Plus';
import MinusImg from '../../../assets/images/Minus';
import SvgIcon from '../../../components/SvgIcon';

const HeightQuestion = (props) => {
  const [height, setHeight] = useState(-160);
  const scaleNumbers = [210, 180, 150, 120, 90, 60];
  const increase = () => {
    var val = height - 1;
    setValue(val);
  }
  const decrease = () => {
    var val = height + 1;
    setValue(val);
  }
  const setValue = (val) => {
    setHeight(val);
    const name = props.questions && props.questions[0].name;
    props.setValues && props.setValues([{ name, value: -1 * val }]);
  }
  return (<View style={styles.container}>
    <Text style={styles.question}>What is your height?</Text>
    <View style={{ flex: 1, paddingTop: 50, alignItems: 'center', justifyContent: 'center' }}>
      <Text style={styles.value}>{(-1 * height)} <Text style={styles.mini}>cm</Text></Text>
      <View style={{ paddingTop: 50, flexDirection: 'row' }}>
        <View style={styles.scale}>
          {scaleNumbers.map((i, key) =>
            <Text key={key} style={styles.scaletext}>
              {i} --
          </Text>
          )}
        </View>
        <Slider
          style={styles.slider}
          orientation={'vertical'}
          value={height}
          onValueChange={setValue}
          step={1}
          maximumTrackTintColor={'#49D581'}
          minimumTrackTintColor={'#CBCBCB'}
          maximumValue={-60}
          minimumValue={-210}
          thumbTintColor={'white'}
          thumbStyle={styles.thumb}
          thumbTouchSize={{ width: 60, height: 60 }}
        />
        <View style={styles.bTopContainer}>
          <View style={styles.bContainer}>
            <SvgIcon onPress={increase} size={40} svg={PlusImg}>
            </SvgIcon>
            <SvgIcon onPress={decrease} size={40} svg={MinusImg}>
            </SvgIcon>
          </View>
        </View>
      </View>

    </View>
  </View>)
}

const styles = StyleSheet.create({
  bContainer: {
    height: 140,
    justifyContent: 'space-around',
    paddingLeft: 30,
    alignSelf: 'flex-end'
  },
  bTopContainer: {
    justifyContent: 'center',
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
    height: 350,
    flexDirection: 'column-reverse', justifyContent: "flex-end"
  },
  scale: {
    justifyContent: 'space-between',
    paddingVertical: 10
  },
  scaletext: {
    fontSize: 10,
    color: '#CBCBCB'
  },
  thumb: {
    padding: 15,
    elevation: 3,
    alignItems: 'center',
    alignSelf: 'flex-end',
    justifyContent: 'center',
    borderRadius: 50
  },
  mini: {
    fontSize: 12,
  }
})

export default HeightQuestion