
/**
 * Following function is inspired by Blogger's select label function
 * Taken and edited from http://www.blogger.com/v-app/scripts/720937736-richedit.blogger.js
 * 2007/09/28
 * Responds to a click on a label by adding that label to the input box. */

function BrowseAddTag(tag) {
  var tagInput = document.getElementById("post-labels");
  if (!tagInput) return;
  var curVal = Trim(tagInput.value);

  if (curVal == "") {
    tagInput.value = tag.innerHTML;
  } else {
    // Remove excess whitespace
    var newTag = Trim(tag.innerHTML);
    var tags = curVal.split(',');
    var found = false;
    // See if the label already is in the text box
    for (var i=0; i < tags.length; i++) {
      tags[i] = Trim(tags[i]);
      if (tags[i] == newTag) found = true;
    }
    // If not, add it.
    if (!found) {
      tags[tags.length] = newTag;
    }
    // Remove any whitespace-only elements from the array.
    var newTags = new Array();
    for (var i=0; i < tags.length; i++) {
      if (tags[i] != "") {
        newTags[newTags.length] = tags[i];
      }
    }
    // Put it back together.
    tagInput.value = newTags.join(", ") + ", ";
  }
}
