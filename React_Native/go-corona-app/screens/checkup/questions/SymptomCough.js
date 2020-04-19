import React, { useState } from 'react';
import { StyleSheet, Text, View, ScrollView } from 'react-native';
import { Divider, RadioButton } from 'react-native-paper';
import { CheckBox } from 'react-native-elements';
import TermsImage from '../../../assets/images/IntersectionTerms.svg';



export default function SymptomCough() {
           
    const [checkedItems, setCheckedItems] = useState({dc:false,cws:false,cwcp:false,cwap:false,cwb:false});
    const symps =[
        {
            name: 'Dry cough',
            key: 'dc'
            
          },
          {
            name: 'Cough with sputum',
            key: 'cws'
            
          },
          {
            name: 'Cough with chest pain',
            key: 'cwcp'
           
          },
          {
              name: 'Cough with abdominal pain',
              key: 'cwap'
             
            },
          {
              name: 'Cough with blood in it',
              key: 'cwb'
             
           },
          
    ];

    const [values, setValues] = useState('Yes')
    
    const handleChange = (item) => {
        setCheckedItems({...checkedItems, [item.key] : !checkedItems[item.key] });
        //console.log("checkedItems: ", checkedItems);
    }
  return (
    <ScrollView>
    <View style={styles.viewContainer}>
            <View style={styles.sectionContainer}>
            <Text style={styles.title}>Please tell us about your symptoms</Text>
            <TermsImage style={styles.image} width="120" height="120" />
            </View>
          <Text style={styles.textSty}>Do you have cough?</Text> 
          <Divider  />
          <View  style={styles.agreeContainer}>
          <RadioButton.Group
          onValueChange={values => setValues(values)}
          value={values}
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
          <Divider />
        </View> 
        </RadioButton.Group>
        </View>
      <Text style={styles.textSty}>If yes, then check the following</Text> 
      <View style={styles.agreeContainer}>
      { symps.map((item)=>(
      <View key={item.key}>
      <CheckBox key={item.key} containerStyle={styles.contStyle} textStyle={styles.txtStyle} title={item.name} checked={checkedItems[item.key]} onPress={()=>handleChange(item)} uncheckedColor='#D2D2D2' checkedColor='#E03D51'/>
      <Divider  />
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
