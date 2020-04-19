import React, { useState } from 'react';
import { StyleSheet, Text, View, ScrollView } from 'react-native';
import { Divider, RadioButton } from 'react-native-paper';
import { CheckBox } from 'react-native-elements'


export default function HealthHistory() {
    const [checkedItems, setCheckedItems] = useState({asthma:false,highBP:false,kidneyDisease:false,heartDisease:false,lungDisease:false,stroke:false,diabetes:false,no:false});
    const [value, setValue] = useState('first')
    const [valueNext, setValueNext] = useState('Yes')

    const diseases = [
        {
          name: 'Asthma',
          key: 'asthma'
          
        },
        {
          name: 'High BP',
          key: 'highBP'
          
        },
        {
          name: 'Kidney disease',
          key: 'kidneyDisease'
         
        },
        {
            name: 'Heart disease',
            key: 'heartDisease'
           
          },
        {
            name: 'Lung disease',
            key: 'lungDisease'
           
         },
          {
            name: 'Stroke',
            key: 'stroke'
           
          },
          {
            name: 'Diabetes',
            key: 'diabetes'
           
          },
          {
            name: 'No, I do not have any of the above health issues',
            key: 'no'
           
          }
        
      ];

    
      const handleChange = (item) => {
        setCheckedItems({...checkedItems, [item.key] : !checkedItems[item.key] });
        //console.log("checkedItems: ", checkedItems);
    }
    
  return (
    <ScrollView>
    <View style={styles.viewContainer}>
            <View style={styles.sectionContainer}>
                <Text style={styles.title}>Please check all the statements below that apply to you</Text>
            </View>
            <Text style={styles.subTitle}>Select one in each row</Text>
            <Text style={styles.textSty}>Do you have had a health history?</Text>  
            <Divider  />
            <View style={styles.agreeContainer}>
            { diseases.map((item)=>(
            <View key={item.key}>
            <CheckBox key={item.key} containerStyle={styles.contStyle} textStyle={styles.txtStyle} title={item.name} checked={checkedItems[item.key]} onPress={()=>handleChange(item)} uncheckedColor='#D2D2D2' checkedColor='#E03D51'/>
            <Divider  />
            </View>
            )
            )}
          </View>
          <Text style={styles.textSty}>Do you smoke cigarettes?</Text> 
          <Divider  />
          <View  style={styles.agreeContainer}>
          <RadioButton.Group
          onValueChange={value => setValue(value)}
          value={value}
           >
          <View style={styles.agreeContainer}>
          <View style={styles.radAlign}>
           <RadioButton.Android value="first" color="#E03D51" uncheckedColor="#D2D2D2" />
           <Text style={styles.radTxt}>Yes</Text>
           </View>
           <Divider  />
          </View>
          <View  style={styles.agreeContainer}>
            <View style={styles.radAlign}>
            <RadioButton.Android value="second" color="#E03D51" uncheckedColor="#D2D2D2" />
            <Text style={styles.radTxt}>No</Text>
            </View>
            <Divider />
          </View>
        </RadioButton.Group>
        </View>
        <Text style={styles.textSty}>Do you have low immunity due to organ transplant or HIV?</Text> 
        <View  style={styles.agreeContainer}>
        <RadioButton.Group
        onValueChange={valueNext => setValueNext(valueNext)}
        value={valueNext}
         >
        <View style={styles.agreeContainer}>
        <View style={styles.radAlign}>
         <RadioButton.Android value="Yes" color="#E03D51" uncheckedColor="#D2D2D2" />
         <Text style={styles.radTxt}>Yes</Text>
         </View>
         <Divider  />
        </View>
        <View  style={styles.agreeContainer}>
          <View style={styles.radAlign}>
          <RadioButton.Android value="No" color="#E03D51" uncheckedColor="#D2D2D2" />
          <Text style={styles.radTxt}>No</Text>
          </View>
          <Divider  />
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
  contStyle:{
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
      marginLeft:10,
      marginBottom:25
  },
  helpLinks: {
      marginTop: 30,
      padding: 5,
      width: "90%"
  },
  subTitle:{
    alignSelf:'flex-start', 
    marginLeft:25,
  },
  agreeContainer: {
    marginLeft: 10,
    marginRight: 10,
    flexDirection:'column'
  },
  txtStyle:{
    fontWeight: 'normal'
  },
  textSty:{
    marginLeft:24,
    marginBottom:20,
    marginTop: 40
  },
  radAlign:{
    flexDirection:'row'
  },
  radTxt:{
    marginTop:8
  }
});
