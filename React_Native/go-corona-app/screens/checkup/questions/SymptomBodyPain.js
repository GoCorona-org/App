import React, { useState } from 'react';
import { StyleSheet, Text, View, ScrollView } from 'react-native';
import { Divider, RadioButton } from 'react-native-paper';
import { CheckBox } from 'react-native-elements';
import TermsImage from '../../../assets/images/IntersectionTerms.svg';



export default function SymptomSoreThroat() {
    const [checkedItems, setCheckedItems] = useState({headache:false,chills:false,nausea:false,vomit:false,diarrhea:false,cc:false});
    const symptoms =[
        {
            name: 'Muscle Pain',
            value: 'mp'
            
          },
          {
            name: 'Joint Pain',
            value: 'jp'
            
          },
          {
            name: 'None',
            value: 'ne'
           
          }
    ];
    const symps =[
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
              key: 'vomit'
             
            },
          {
              name: 'Diarrhea',
              key: 'diarrhea'
             
           },
           {
            name: 'Conjunctival congestion',
            key: 'cc'
           
         }
    ];

    const [values, setValues] = useState('mp')
    const [valueNext, setValueNext] = useState('Yes')
    
    const handleChange = (item) => {
        setCheckedItems({...checkedItems, [item.key] : !checkedItems[item.key] });
        //console.log("checkedItems: ", checkedItems);
    }
  return (
    <ScrollView>
    <View style={styles.viewContainer}>
            <View style={styles.sectionContainer}>
            <Text style={styles.title}>Do you have any other symptoms?</Text>
            <TermsImage style={styles.image} width="120" height="120" />
            </View>
          <Text style={styles.textSty}>Do you have any kind of body pain?</Text> 
          <Divider  />
          <View  style={styles.agreeContainer}>
          <RadioButton.Group
          onValueChange={values => setValues(values)}
          value={values}
           >
           { symptoms.map((item)=>(
            <View style={styles.agreeContainer} key={item.value}>
            <View style={styles.radAlign} key={item.value}>
             <RadioButton.Android value={item.value} color="#E03D51" uncheckedColor="#D2D2D2" />
             <Text style={styles.radTxt}>{item.name}</Text>
             </View>
             <Divider  />
            </View>
                )
                )}
        </RadioButton.Group>
        </View>
        <Text style={styles.textSty}>Do you feel fatigued or drowsy?</Text> 
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
          <Divider />
        </View>  
      </RadioButton.Group>
      </View>
      <Text style={styles.textSty}>Please check if you have any of the following symptoms</Text> 
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
