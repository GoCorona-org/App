import React, { useState } from 'react';
import { StyleSheet, Text, View, ScrollView } from 'react-native';
import { RadioButton } from 'react-native-paper';

export default function CheckupGender(props) {
  const [gender, setGender] = useState('male')
  const setValue = (val) => {
    setGender(val);
    props.setValues && props.setValues({ 'gender': val });
  }
  return (
    <ScrollView>
      <View style={styles.viewContainer}>
        <View style={styles.container}>
          <View style={styles.sectionContainer}>
            <Text style={styles.title}>Please select your gender</Text>
            <View style={styles.agreeContainer}>
              <RadioButton.Group
                onValueChange={valueNext => setValue(valueNext)}
                value={gender}
              >
                <View style={styles.agreeContainer}>
                  <View style={styles.radAlign}>
                    <RadioButton.Android value="male" color="#E03D51" uncheckedColor="#D2D2D2" />
                    <Text style={styles.radTxt}>Male</Text>
                  </View>

                </View>
                <View style={styles.agreeContainer}>
                  <View style={styles.radAlign}>
                    <RadioButton.Android value="female" color="#E03D51" uncheckedColor="#D2D2D2" />
                    <Text style={styles.radTxt}>Female</Text>
                  </View>

                </View>
                <View style={styles.agreeContainer}>
                  <View style={styles.radAlign}>
                    <RadioButton.Android value="Others" color="#E03D51" uncheckedColor="#D2D2D2" />
                    <Text style={styles.radTxt}>Others</Text>
                  </View>

                </View>
              </RadioButton.Group>

            </View>
          </View>
        </View>
      </View>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  viewContainer: {
    flex: 1,
    backgroundColor: '#fafafa',
    justifyContent: 'flex-start',
  },
  container: {
    justifyContent: 'center'
  },
  sectionContainer: {
    marginTop: 40,
    marginBottom: 40,
    marginLeft: 40
  },
  title: {
    fontSize: 18,
    fontWeight: "bold",
    alignSelf: 'flex-start',
    marginLeft: 10,
    marginBottom: 30
  },
  helpLinks: {
    marginTop: 30,
    padding: 5,
    width: "90%"
  },
  agreeContainer: {

    marginRight: 10,
    flexDirection: 'column'
  },
  radAlign: {
    flexDirection: 'row',
    marginBottom: 10
  },
  radTxt: {
    marginTop: 8
  }
});
