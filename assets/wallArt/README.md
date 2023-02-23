# Image Sourcing
Images are currently sourced from Unsplash and are randomly obtained from the following url:

https://source.unsplash.com/random/500x500?nature

Note: Image dimensions are 500px by 500px. Naming convention is `art{i}.jpg`. All images in this directory are automatically loaded by the `TextureManager`. When a new image is added, make sure to modify `TextureManager`'s `loadArtImages` method to account for the additional image.

Licensing info from unsplash should be good for our uses:
```
- All photos can be downloaded and used for free
- Commercial and non-commercial purposes
- No permission needed (though attribution is appreciated!)
```