import * as WebBrowser from 'expo-web-browser';
import React, { Component } from 'react';
import { Image, Platform, Dimensions, StyleSheet, Text, TouchableOpacity, View, ActivityIndicator } from 'react-native';
import { ScrollView } from 'react-native-gesture-handler';
import MapView, { PROVIDER_GOOGLE, Heatmap } from 'react-native-maps';
import Constants from 'expo-constants';
import * as Location from 'expo-location';
import Http from '../services/Http';
import data from '../test_data/location_history.js'
import { csoptsApi } from '../constants/AppSettings';

// TODO: move to constants
const latitudeDelta = 0.09;
const longitudeDelta = 0.0121;

export default class HomeScreen extends Component {
  state = {
    loading: true,
    region: null,
    points: [],
    error: null,
    mapWidth: '99%'
  }
  map = null;
  async componentDidMount() {
    let { status } = await Location.requestPermissionsAsync();
    if (status !== 'granted') {
      // TODO: move to string constants
      this.setState({ error: 'Location permission is needed' })
      return;
    }
    const location = await Location.getCurrentPositionAsync({});
    const { latitude, longitude } = location.coords;
    // TODO: call rest api to get nearby hotspots providing current location details
    const cspotsResponse = await Http.post(csoptsApi);
    var points = this.getHeatMapPoints(cspotsResponse.data);
    this.setHeatMapPoints(latitude, longitude, points);
  }

  onMapReady = () => {
    this.map.animateToRegion(this.state.region);
    setTimeout(() => {
      this.setState({
        mapWidth: '100%'
      })
    }, 100);
  }

  setHeatMapPoints(latitude, longitude, points) {
    this.setState({
      region: {
        latitude,
        longitude,
        latitudeDelta,
        longitudeDelta
      },
      loading: false,
      points
    });
  }

  getHeatMapPoints(data) {
    return data.locations.map((location) => {
      let { latitudeE7, longitudeE7 } = location;
      if (latitudeE7 > 900000000)
        latitudeE7 = latitudeE7 - 4294967296;
      if (longitudeE7 > 1800000000)
        longitudeE7 = longitudeE7 - 4294967296;
      let latitude = latitudeE7 / 10000000;
      let longitude = longitudeE7 / 10000000;
      return {
        latitude,
        longitude
      };
    });
  }

  render() {
    const { loading, points, mapWidth } = this.state;
    return (
      <View style={styles.container}>
        {this.state.loading ?
          <ActivityIndicator></ActivityIndicator> :
          <MapView
            ref={(ref) => { this.map = ref }}
            provider={PROVIDER_GOOGLE}
            onMapReady={this.onMapReady}
            showsUserLocation={true}
            showsMyLocationButton={true}
            style={[styles.map, { width: mapWidth }]}
          >
            <Heatmap
              style={styles.mapStyle}
              points={points}
            />
          </MapView>
        }
      </View>
    );
  }

}

HomeScreen.navigationOptions = {
  header: null,
};


const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
  map: {
    ...StyleSheet.absoluteFillObject,
  },
});

